package beans;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.*;

@Entity
@Table(name = "frequencia")
public class Frequencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="frequencia_codigo", sequenceName="frequencia_codigo", allocationSize=1)
    @GeneratedValue(generator="frequencia_codigo", strategy= GenerationType.SEQUENCE)
    @Column(name = "codigo")
    private Long codigo;
    @Column(name = "aula")
    private BigInteger aula;
    @Column(name = "presenca")
    private Character presenca;
    @JoinColumn(name = "codigo_aluno", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Pessoa aluno;
    @JoinColumn(name = "codigo_turma", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Turma turma;

    public Frequencia() {
    }

    public Frequencia(Long codigo) {
        this.codigo = codigo;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public BigInteger getAula() {
        return aula;
    }

    public void setAula(BigInteger aula) {
        this.aula = aula;
    }

    public Character getPresenca() {
        return presenca;
    }

    public void setPresenca(Character presenca) {
        this.presenca = presenca;
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
        if (!(object instanceof Frequencia)) {
            return false;
        }
        Frequencia other = (Frequencia) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication3.Frequencia[ codigo=" + codigo + " ]";
    }
    
}
