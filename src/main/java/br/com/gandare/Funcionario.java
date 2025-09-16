// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
import java.math.BigDecimal;
import java.time.LocalDate;

class Funcionario extends Pessoa {
    private BigDecimal salario;
    private String funcao;

    public Funcionario(String var1, LocalDate var2, BigDecimal var3, String var4) {
        super(var1, var2);
        this.salario = var3;
        this.funcao = var4;
    }

    public BigDecimal getSalario() {
        return this.salario;
    }

    public void setSalario(BigDecimal var1) {
        this.salario = var1;
    }

    public String getFuncao() {
        return this.funcao;
    }

    public void setFuncao(String var1) {
        this.funcao = var1;
    }
}
