package beans;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "turma")
public class Turma implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="turma_codigo", sequenceName="turma_codigo", allocationSize=1)
    @GeneratedValue(generator="turma_codigo", strategy= GenerationType.SEQUENCE)
    @Column(name = "codigo")
    private Long codigo;
    @Column(name = "datainicio")
    private String datainicio;
    @Column(name = "datafim")
    private String datafim;
    @Column(name = "quantidadeaulas")
    private BigInteger quantidadeaulas;
    @JoinColumn(name = "codigo_disciplina", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Disciplina disciplina;
    @JoinColumn(name = "codigo_professor", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Pessoa professor;

    public String getDisciplinaProfessor(){
        return disciplina.getNome()+" - "+professor.getNome();
    }

    public Turma() {}

    public Turma(Long codigo) {
        this.codigo = codigo;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getDatainicio() {
        return datainicio;
    }

    public void setDatainicio(String datainicio) {
        this.datainicio = datainicio;
    }

    public String getDatafim() {
        return datafim;
    }

    public void setDatafim(String datafim) {
        this.datafim = datafim;
    }

    public BigInteger getQuantidadeaulas() {
        return quantidadeaulas;
    }

    public void setQuantidadeaulas(BigInteger quantidadeaulas) {
        this.quantidadeaulas = quantidadeaulas;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Pessoa getProfessor() {
        return professor;
    }

    public void setProfessor(Pessoa professor) {
        this.professor = professor;
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
        if (!(object instanceof Turma)) {
            return false;
        }
        Turma other = (Turma) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return disciplina.getNome()+" - "+professor.getNome();
    }
    
}
