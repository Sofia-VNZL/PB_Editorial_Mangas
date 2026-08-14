import { useState, useEffect } from "react";
import "./AvaliacaoModal.css";

const API_EDITORIAL = "http://localhost:8080/api";
const API_AVALIACAO = "http://localhost:8081/api";

export default function AvaliacaoModal({ manga, onClose }) {
    if (!manga) return null;

    const [media, setMedia] = useState(null);
    const [total, setTotal] = useState(0);
    const [notaSelecionada, setNotaSelecionada] = useState(0);
    const [hover, setHover] = useState(0);
    const [enviando, setEnviando] = useState(false);
    const [mensagem, setMensagem] = useState("");

    useEffect(() => {
        carregarMedia();
    }, [manga.id]);

    async function carregarMedia() {
        try {
            const r = await fetch(
                `${API_EDITORIAL}/mangas/${manga.id}/detalhes`
            );
            const data = await r.json();
            setMedia(data.mediaAvaliacoes);
            setTotal(data.totalAvaliacoes);
        } catch {
            setMedia(0);
            setTotal(0);
        }
    }

    async function enviarAvaliacao() {
        if (notaSelecionada === 0) {
            setMensagem("Seleciona uma nota antes de enviar.");
            return;
        }
        setEnviando(true);
        try {
            await fetch(`${API_AVALIACAO}/avaliacoes`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ mangaId: manga.id, nota: notaSelecionada }),
            });
            setMensagem("Avaliação enviada! Obrigada");
            setNotaSelecionada(0);
            carregarMedia();
        } catch {
            setMensagem("Erro ao enviar avaliação" );
        } finally {
            setEnviando(false);
        }
    }

    function estrelas(nota) {
        return "★".repeat(Math.round(nota)) + "☆".repeat(5 - Math.round(nota));
    }

    return (
        <div className="aval-overlay" onClick={onClose}>
            <div className="aval-modal" onClick={(e) => e.stopPropagation()}>
                <button className="aval-close" onClick={onClose}>✕</button>

                <h2 className="aval-titulo">{manga.titulo}</h2>
                <p className="aval-autor">por {manga.autor?.nome || "Autor desconhecido"}</p>

                <div className="aval-media">
          <span className="aval-estrelas-media">
            {media !== null ? estrelas(media) : "☆☆☆☆☆"}
          </span>
                    <span className="aval-numero">
            {media !== null ? media.toFixed(1) : "—"} ({total} avaliações)
          </span>
                </div>

                <div className="aval-divider" />

                <p className="aval-label">A tua nota:</p>
                <div className="aval-estrelas">
                    {[1, 2, 3, 4, 5].map((n) => (
                        <span
                            key={n}
                            className={`aval-estrela ${n <= (hover || notaSelecionada) ? "ativa" : ""}`}
                            onClick={() => setNotaSelecionada(n)}
                            onMouseEnter={() => setHover(n)}
                            onMouseLeave={() => setHover(0)}
                        >
              ★
            </span>
                    ))}
                </div>

                {mensagem && <div className="aval-mensagem">{mensagem}</div>}

                <button
                    className="aval-btn"
                    onClick={enviarAvaliacao}
                    disabled={enviando}>
                    {enviando ? "A enviar..." : "Enviar avaliação"}
                </button>
            </div>
        </div>
    );
}

//revisar refac depois