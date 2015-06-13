package beans;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "alunoturma")
public class Alunoturma implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="alunoturma_codigo", sequenceName="alunoturma_codigo", allocationSize=1)
    @GeneratedValue(generator="alunoturma_codigo", strategy= GenerationType.SEQUENCE)
    @Column(name = "codigo")
    private Long codigo;
    @JoinColumn(name = "codigo_aluno", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Pessoa aluno;
    @JoinColumn(name = "codigo_turma", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Turma turma;

    public Alunoturma() {
    }

    public Alunoturma(Long codigo) {
        this.codigo = codigo;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Pessoa getAluno() {
        return aluno;
    }

    public void setAluno(Pessoa aluno) {
        this.aluno = aluno;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
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
        if (!(object instanceof Alunoturma)) {
            return false;
        }
        Alunoturma other = (Alunoturma) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication3.Alunoturma[ codigo=" + codigo + " ]";
    }
    
}
