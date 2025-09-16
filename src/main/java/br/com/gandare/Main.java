import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final DateTimeFormatter DF_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DecimalFormat DF_MOEDA;

    static {
        DecimalFormatSymbols br = new DecimalFormatSymbols(new Locale("pt", "BR"));
        br.setDecimalSeparator(',');
        br.setGroupingSeparator('.');
        DF_MOEDA = new DecimalFormat("#,##0.00", br);
    }

    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>(List.of(
                new Funcionario("Maria", LocalDate.of(2000,10,18), bd("2009.44"), "Operador"),
                new Funcionario("João", LocalDate.of(1990,5,12), bd("2284.38"), "Operador"),
                new Funcionario("Caio", LocalDate.of(1961,5,2), bd("9836.14"), "Coordenador"),
                new Funcionario("Miguel", LocalDate.of(1988,10,14), bd("19119.88"), "Diretor"),
                new Funcionario("Alice", LocalDate.of(1995,1,5), bd("2234.68"), "Recepcionista"),
                new Funcionario("Heitor", LocalDate.of(1999,11,19), bd("1582.72"), "Operador"),
                new Funcionario("Arthur", LocalDate.of(1993,3,31), bd("4071.84"), "Contador"),
                new Funcionario("Laura", LocalDate.of(1994,7,8), bd("3017.45"), "Gerente"),
                new Funcionario("Heloísa", LocalDate.of(2003,5,24), bd("1606.85"), "Eletricista"),
                new Funcionario("Helena", LocalDate.of(1996,9,2), bd("2799.93"), "Gerente")
        ));

        funcionarios.removeIf(f -> f.getNome().equalsIgnoreCase("João"));

        System.out.println("=== Funcionários ===");
        imprimirLista(funcionarios);

        funcionarios.forEach(f -> f.setSalario(f.getSalario().multiply(bd("1.10")).setScale(2, RoundingMode.HALF_UP)));
        System.out.println("\n=== Após aumento 10% ===");
        imprimirLista(funcionarios);

        Map<String, List<Funcionario>> porFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao, LinkedHashMap::new, Collectors.toList()));
        System.out.println("\n=== Agrupados por função ===");
        porFuncao.forEach((func, lista) -> {
            System.out.println("Função: " + func);
            imprimirLista(lista);
            System.out.println();
        });

        System.out.println("=== Aniversariantes Outubro/Dezembro ===");
        funcionarios.stream()
                .filter(f -> {
                    int m = f.getDataNascimento().getMonthValue();
                    return m == 10 || m == 12;
                })
                .forEach(Main::imprimirLinha);

        Funcionario maisVelho = funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElse(null);
        System.out.println("\n=== Mais velho ===");
        if (maisVelho != null) {
            System.out.printf("Nome: %s | Idade: %d%n", maisVelho.getNome(), maisVelho.getIdade());
        }

        System.out.println("\n=== Ordem alfabética ===");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome, String.CASE_INSENSITIVE_ORDER))
                .forEach(Main::imprimirLinha);

        BigDecimal total = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\n=== Total salários ===");
        System.out.println("Total: " + DF_MOEDA.format(total));

        BigDecimal salarioMinimo = bd("1212.00");
        System.out.println("\n=== Equivalente em salários mínimos ===");
        funcionarios.forEach(f -> {
            BigDecimal qtd = f.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.printf("%s: %s salários mínimos%n", f.getNome(), DF_MOEDA.format(qtd));
        });
    }

    private static BigDecimal bd(String v) { return new BigDecimal(v); }

    private static void imprimirLista(List<Funcionario> lista) {
        lista.forEach(Main::imprimirLinha);
    }

    private static void imprimirLinha(Funcionario f) {
        String data = f.getDataNascimento().format(DF_DATA);
        String sal = DF_MOEDA.format(f.getSalario());
        System.out.printf("Nome: %-10s | Nasc.: %s | Salário: %s | Função: %s%n",
                f.getNome(), data, sal, f.getFuncao());
    }
}

