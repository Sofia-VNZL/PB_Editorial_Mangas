import MangaCard from "./MangaCard";
import "./MangaGrid.css";

export default function MangaGrid({ mangas, loading, online, onSelect, onEditar, onDeletar }) {
    return (
        <div>
            <div className="section-title">Catálogo de obras ({mangas.length})</div>
            {loading ? (
                <div className="grid">
                    {Array(4).fill(0).map((_, i) => <div key={i} className="skeleton" />)}
                </div>
            ) : mangas.length === 0 ? (
                <div className="empty-state">
                    {online ? "Nenhuma obra encontrada." : "Não foi possível conectar à API. Inicia o Spring Boot primeiro!!!"}
                </div>
            ) : (
                <div className="grid">
                    {mangas.map(m => (
                        <MangaCard key={m.id} manga={m}
                                   onClick={onSelect} onEditar={onEditar} onDeletar={onDeletar} />
                    ))}
                </div>
            )}
        </div>
    );
}