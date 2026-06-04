import { coverColor, GenreBadge, StatusBadge } from "./MangaCard.jsx";
import "./Modal.css";

export default function Modal({ manga, onClose }) {
    if (!manga) return null;
    const [c1, c2] = coverColor(manga.id || 1);
    const autorNome = manga.autor?.nome || "Autor desconhecido";

    return (
        <div className="modal-overlay" onClick={onClose}>
            <div className="modal" onClick={e => e.stopPropagation()}>
                <button className="modal-close" onClick={onClose}>✕</button>

                <div className="modal-cover"
                     style={{ background: `linear-gradient(135deg, ${c1}, ${c2})` }}>
                    {manga.titulo}
                </div>

                <div className="modal-badges">
                    <GenreBadge genre={manga.genero} />
                    <StatusBadge status={manga.status} />
                </div>

                <div className="modal-title">{manga.titulo}</div>
                <div className="modal-author">por {autorNome}</div>
                <div className="modal-sinopse">
                    {manga.sinopse || "Sem sinopse disponível."}
                </div>
            </div>
        </div>
    );
}