package org.example.entities;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.StringJoiner;
import java.util.regex.Pattern;

public class Usuario extends _BaseEntity{


    private static final Pattern TelP = Pattern.compile("^\\s*(\\d{2})[-. ]?(\\d{5}|\\d{4})[-. ]?(\\d{4})[-. ]?\\s*$");
    private static final Pattern EmailP = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
    private static final Pattern SenhaP = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
    DateTimeFormatter dataFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private String nome;
    private String sobrenome;
    private LocalDate dataNascimento;
    private String telefone;
    private String email;
    private String senha;

    public Usuario() {
    }

    public Usuario(int id, String nome, String sobrenome, LocalDate dataNascimento, String telefone, String email, String senha) {
        super(id);
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(int id, String nome, String sobrenome, String dataNascimento, String telefone, String email, String senha) {
        super(id);

        this.nome = nome;
        this.sobrenome = sobrenome;


        try {
            setDataNascimento(dataNascimento);
            setTelefone(telefone);
            setEmail(email);
            setSenha(senha);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        try {
            if (dataNascimento == null || !isDateValid(dataNascimento)) {
                throw new IllegalArgumentException("Data de nascimento inválida");
            } else {
                this.dataNascimento = LocalDate.parse(dataNascimento, dataFormat);
                if (!isMaiorDeIdade()) {
                    this.dataNascimento = null;
                    throw new IllegalArgumentException("Menor de idade");
                }
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data de nascimento inválida");
        }
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        if (!TelP.matcher(telefone).matches()) {
            throw new IllegalArgumentException("Telefone inválido");
        }
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (!EmailP.matcher(email).matches()) {
            throw new IllegalArgumentException("Email inválido");
        }
        this.email = email;
    }



    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        if (!SenhaP.matcher(senha).matches()) {
            throw new IllegalArgumentException("Senha inválida");
        }
        this.senha = senha;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Usuario.class.getSimpleName() + "[", "]")
                .add("dataFormat=" + dataFormat)
                .add("nome='" + nome + "'")
                .add("sobrenome='" + sobrenome + "'")
                .add("dataNascimento=" + dataNascimento)
                .add("telefone='" + telefone + "'")
                .add("email='" + email + "'")
                .add("senha='" + senha + "'")
                .toString();
    }

    public static boolean isDateValid(String strDate) {
        String dateFormat = "dd/MM/uuuu";

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                .ofPattern(dateFormat)
                .withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDate date = LocalDate.parse(strDate, dateTimeFormatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public boolean isMaiorDeIdade() {
        LocalDate now = LocalDate.now();
        Period period = Period.between(dataNascimento, now);
        return period.getYears() >= 18;
    }

}
