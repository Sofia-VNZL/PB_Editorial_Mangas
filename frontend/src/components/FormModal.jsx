import { useState, useEffect } from "react";
import "./FormModal.css";

const API = "http://localhost:8080/api";

const GENEROS = ["SHONEN","SHOJO","SEINEN","JOSEI","ISEKAI",
    "SLICE_OF_LIFE","HORROR","ROMANCE","FANTASY","ACTION","SPORTS"];
const STATUS_LIST = ["EM_PUBLICACAO","CONCLUIDO","PAUSADO","CANCELADO","ANUNCIADO"];

export default function FormModal({ manga, autores, onClose, onSaved }) {
    const [form, setForm] = useState({
        titulo: "", sinopse: "", genero: "SHONEN",
        status: "EM_PUBLICACAO", autorId: ""
    });
    const [erro, setErro] = useState("");

    useEffect(() => {
        if (manga) {
            setForm({
                titulo: manga.titulo || "",
                sinopse: manga.sinopse || "",
                genero: manga.genero || "SHONEN",
                status: manga.status || "EM_PUBLICACAO",
                autorId: manga.autor?.id || ""
            });
            setErro("");
        }
    }, [manga]);

    if (manga === null) return null;

    const isEdicao = !!manga.id;

    function set(field, value) {
        setForm(f => ({ ...f, [field]: value }));
    }

    async function salvar() {
        if (!form.titulo.trim()) { setErro("Título é obrigatório."); return; }
        if (!form.autorId) { setErro("Seleciona um autor."); return; }

        const body = {
            titulo: form.titulo,
            sinopse: form.sinopse,
            genero: form.genero,
            status: form.status,
            autor: { id: Number(form.autorId) }
        };

        const url = isEdicao ? `${API}/mangas/${manga.id}` : `${API}/mangas`;
        const method = isEdicao ? "PUT" : "POST";

        try {
            const r = await fetch(url, {
                method,
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(body)
            });
            if (!r.ok) throw new Error();
            onSaved();
        } catch {
            setErro("Erro ao salvar. Verifica se o servidor está ativo.");
        }
    }

    return (
        <div className="form-overlay" onClick={onClose}>
            <div className="form-modal" onClick={e => e.stopPropagation()}>
                <button className="form-close" onClick={onClose}>✕</button>
                <h2 className="form-titulo">{isEdicao ? "Editar Manga" : "Novo Manga"}</h2>

                <label>Título</label>
                <input value={form.titulo} onChange={e => set("titulo", e.target.value)}
                       placeholder="Ex: One Piece" />

                <label>Sinopse</label>
                <textarea value={form.sinopse} onChange={e => set("sinopse", e.target.value)}
                          placeholder="Descrição da obra..." rows={3} />

                <label>Gênero</label>
                <select value={form.genero} onChange={e => set("genero", e.target.value)}>
                    {GENEROS.map(g => (
                        <option key={g} value={g}>
                            {g.replace("_", " ").toLowerCase().replace(/^\w/, c => c.toUpperCase())}
                        </option>
                    ))}
                </select>

                <label>Status</label>
                <select value={form.status} onChange={e => set("status", e.target.value)}>
                    {STATUS_LIST.map(s => (
                        <option key={s} value={s}>
                            {{ EM_PUBLICACAO:"Em publicação", CONCLUIDO:"Concluído",
                                PAUSADO:"Pausado", CANCELADO:"Cancelado", ANUNCIADO:"Anunciado" }[s]}
                        </option>
                    ))}
                </select>

                <label>Autor</label>
                <select value={form.autorId} onChange={e => set("autorId", e.target.value)}>
                    <option value="">Seleciona um autor</option>
                    {autores.map(a => (
                        <option key={a.id} value={a.id}>{a.nome}</option>
                    ))}
                </select>

                {erro && <div className="form-erro">{erro}</div>}

                <div className="form-buttons">
                    <button className="btn-cancelar" onClick={onClose}>Cancelar</button>
                    <button className="btn-salvar" onClick={salvar}>
                        {isEdicao ? "Salvar alterações" : "Cadastrar"}
                    </button>
                </div>
            </div>
        </div>
    );
}