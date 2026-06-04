import AutorCard from "./AutorCard";
import "./MangaGrid.css";

export default function AutorGrid({ autores, onEditar, onDeletar }) {
    return (
        <div>
            <div className="section-title">Autores ({autores.length})</div>
            {autores.length === 0 ? (
                <div className="empty-state">Nenhum autor cadastrado.</div>
            ) : (
                <div className="grid">
                    {autores.map(a => (
                        <AutorCard key={a.id} autor={a} onEditar={onEditar} onDeletar={onDeletar} />
                    ))}
                </div>
            )}
        </div>
    );
}