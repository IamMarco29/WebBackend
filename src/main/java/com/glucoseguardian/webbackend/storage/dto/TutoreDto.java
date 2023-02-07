package com.glucoseguardian.webbackend.storage.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.glucoseguardian.webbackend.storage.entity.Paziente;
import com.glucoseguardian.webbackend.storage.entity.Tutore;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.lang3.Validate;

/**
 * Rappresenta l'output dell'entità tutore.
 */
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class TutoreDto extends RisultatoDto implements Serializable {

  private String codiceFiscale;
  private String nome;
  private String cognome;
  private String dataNascita;
  private String indirizzo;
  private String telefono;
  private String email;
  private String sesso;
  private String relazioneDiParentela;
  private List<PazienteDto> pazienteList;

  public TutoreDto() {
  }

  /**
   * Construttore predefinito di TutoreDto.
   */
  public TutoreDto(String codiceFiscale, String nome, String cognome, String dataNascita,
      String indirizzo, String telefono, String email, String sesso, String relazioneDiParentela,
      List<PazienteDto> pazienteList) {
    this.codiceFiscale = codiceFiscale;
    this.nome = nome;
    this.cognome = cognome;
    this.dataNascita = dataNascita;
    this.indirizzo = indirizzo;
    this.telefono = telefono;
    this.email = email;
    this.sesso = sesso;
    this.relazioneDiParentela = relazioneDiParentela;
    this.pazienteList = pazienteList;
  }

  public String getCodiceFiscale() {
    return codiceFiscale;
  }

  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCognome() {
    return cognome;
  }

  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  public String getDataNascita() {
    return dataNascita;
  }

  public void setDataNascita(String dataNascita) {
    this.dataNascita = dataNascita;
  }

  public String getIndirizzo() {
    return indirizzo;
  }

  public void setIndirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSesso() {
    return sesso;
  }

  public void setSesso(String sesso) {
    this.sesso = sesso;
  }

  public String getRelazioneDiParentela() {
    return relazioneDiParentela;
  }

  public void setRelazioneDiParentela(String relazioneDiParentela) {
    this.relazioneDiParentela = relazioneDiParentela;
  }

  public List<PazienteDto> getPazienteList() {
    return pazienteList;
  }

  public void setPazienteList(List<PazienteDto> pazienteList) {
    this.pazienteList = pazienteList;
  }

  /**
   *  Costruisce un TutoreDto a partire da un {@link Tutore},
   *  il campo password non viene popolato.
   */
  public static TutoreDto valueOf(Tutore tutore) {
    List<PazienteDto> list = new ArrayList<>();
    for (Paziente paziente : tutore.getPazienteList()) {
      list.add(PazienteDto.valueOf(paziente));
    }
    TutoreDto tutoreDto = new TutoreDto();
    tutoreDto.setCodiceFiscale(tutore.getCodiceFiscale());
    tutoreDto.setNome(tutore.getNome());
    tutoreDto.setCognome(tutore.getCognome());
    tutoreDto.setIndirizzo(tutore.getIndirizzo());
    tutoreDto.setTelefono(tutore.getTelefono());
    tutoreDto.setEmail(tutore.getEmail());
    tutoreDto.setSesso(tutore.getSesso() + "");
    tutoreDto.setPazienteList(list);

    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    String dataNascitaPazienteDto = dateFormat.format(tutore.getDataNascita());
    tutoreDto.setDataNascita(dataNascitaPazienteDto);

    return tutoreDto;
  }

  public void validate()throws IllegalArgumentException {
    Validate.notNull(codiceFiscale, "Il codice fiscale non può essere vuoto",
        Pattern.CASE_INSENSITIVE);
    Validate.isTrue(codiceFiscale.length() == 16,
        "La lunghezza del codice fiscale deve essere di 16 caratteri");

    Validate.notNull(nome, "Il nome non puo essere vuoto", Pattern.CASE_INSENSITIVE);
    Validate.isTrue(nome.length() <= 30,
        "La lunghezza del codice fiscale deve essere di 16 caratteri");

    Validate.notNull(cognome, "Il cognome non puo essere vuoto", Pattern.CASE_INSENSITIVE);
    Validate.isTrue(cognome.length() <= 30,
        "La lunghezza del codice fiscale deve essere di 16 caratteri");

    Validate.notNull(sesso, "il sesso non puo' essere vuoto", Pattern.CASE_INSENSITIVE);
    Pattern pattern = Pattern.compile("^M|F$ ");
    Validate.isTrue(pattern.matcher(sesso).matches(), "il sesso non e' valido");

    Validate.notNull(dataNascita, "la data di nascita non puo essere vuota");
    Pattern pattern1 = Pattern.compile(
        "^(0[1-9]|[1-2]\\d|3[01])\\/(0[1-9]|1[0-2])\\/\\d\\d\\d\\d$");
    Validate.isTrue(pattern1.matcher(dataNascita).matches(),
        "la data nascita inserita non e' valida");

    Validate.notNull(email, "la mail non puo essere null");
    Pattern pattern2 = Pattern.compile("^[a-zA-Z0-9.!#$%&’*+/=?^_`{}~-]+@(?:[a-zA-Z0-9-\\.]+)\\w$",
        Pattern.CASE_INSENSITIVE);
    Validate.isTrue(pattern2.matcher(email).matches(), "L'email non è valida");

    Validate.notNull(telefono, "indirizzo non puo essere null");
    Pattern pattern3 = Pattern.compile("^+?\\d{5,15}$");
    Validate.isTrue(pattern3.matcher(telefono).matches(), "il telefono è valido");

    Validate.notNull(indirizzo, "l'indirizzo non puo' essere vuoto", Pattern.CASE_INSENSITIVE);
    Validate.isTrue(indirizzo.length() <= 50, "La lunghezza dell'indirizzo non è valida");

    Validate.notNull(relazioneDiParentela,"relazione di parentela non puo essere vuoto");
    Validate.isTrue(relazioneDiParentela.length() <= 60,"la lunghezza della relazione di parentela non e' valida");
  }
}
