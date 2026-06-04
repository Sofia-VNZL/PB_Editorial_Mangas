import { useState, useEffect } from "react";
import "./FormModal.css";

const API = "http://localhost:8080/api";

export default function AutorFormModal({ autor, onClose, onSaved }) {
    const [form, setForm] = useState({ nome: "", biografia: "" });
    const [erro, setErro] = useState("");

    useEffect(() => {
        if (autor) {
            setForm({
                nome: autor.nome || "",
                biografia: autor.biografia || ""
            });
            setErro("");
        }
    }, [autor]);

    if (autor === null) return null;

    const isEdicao = !!autor.id;

    function set(field, value) { setForm(f => ({ ...f, [field]: value })); }

    async function salvar() {
        if (!form.nome.trim()) { setErro("Nome é obrigatório."); return; }

        const url = isEdicao ? `${API}/autores/${autor.id}` : `${API}/autores`;
        const method = isEdicao ? "PUT" : "POST";

        try {
            const r = await fetch(url, {
                method,
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(form)
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
                <h2 className="form-titulo">{isEdicao ? "Editar Autor" : "Novo Autor"}</h2>

                <label>Nome</label>
                <input value={form.nome} onChange={e => set("nome", e.target.value)}
                       placeholder="Ex: Eiichiro Oda" />

                <label>Biografia</label>
                <textarea value={form.biografia} onChange={e => set("biografia", e.target.value)}
                          placeholder="Breve biografia do autor..." rows={4} />

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