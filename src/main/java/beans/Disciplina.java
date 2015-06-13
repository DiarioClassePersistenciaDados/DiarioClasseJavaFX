package beans;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "disciplina")
public class Disciplina implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="disciplina_codigo", sequenceName="disciplina_codigo", allocationSize=1)
    @GeneratedValue(generator="disciplina_codigo", strategy= GenerationType.SEQUENCE)
    @Column(name = "codigo")
    private Long codigo;
    @Column(name = "nome")
    private String nome;

    public Disciplina() {
    }

    public Disciplina(Long codigo) {
        this.codigo = codigo;
    }

    public Disciplina(Long codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Disciplina)) {
            return false;
        }
        Disciplina other = (Disciplina) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nome;
    }
    
}
