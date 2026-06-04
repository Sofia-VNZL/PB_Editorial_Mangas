import "./AutorCard.css";

export default function AutorCard({ autor, onEditar, onDeletar }) {
    const iniciais = autor.nome?.split(" ").map(p => p[0]).slice(0, 2).join("").toUpperCase();
    return (
        <div className="autor-card">
            <div className="autor-avatar">{iniciais}</div>
            <div className="autor-info">
                <div className="autor-nome">{autor.nome}</div>
                <div className="autor-bio">{autor.biografia || "Sem biografia."}</div>
            </div>
            <div className="autor-actions">
                <button className="btn-edit" onClick={() => onEditar(autor)}>Editar</button>
                <button className="btn-delete" onClick={() => onDeletar(autor.id)}>Deletar</button>
            </div>
        </div>
    );
}