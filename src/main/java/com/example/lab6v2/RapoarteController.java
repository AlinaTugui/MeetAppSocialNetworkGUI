package com.example.lab6v2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import socialnetwork.domain.Raport;
import socialnetwork.domain.Utilizator;
import socialnetwork.service.ServiceManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class RapoarteController implements Initializable {
    private ServiceManager sM = ServiceManager.getInstance();
    public TextField dateActivity;
    public TextField friendName;
    public TextField dateMessages;
    public ObservableList<Raport> modelRapoarte1 = FXCollections.observableArrayList();
    public ObservableList<Raport> modelRapoarte2 = FXCollections.observableArrayList();
    public TableView<Raport> table1;
    public TableView<Raport> table2;
    public TableColumn<Raport, String> tableActivity1;
    public TableColumn<Raport, LocalDate> tableDate1;
    public TableColumn<Raport, String> tableMessages2;
    public TableColumn<Raport, LocalDate> tableDate2;

    private List<Raport> load(LocalDate d1, LocalDate d2) {
        Iterable<Raport> i = sM.getSrvPrietenie().findAllUserDate(MainViewController.getIdLogin(), d1, d2);
        List<Raport> l1 = StreamSupport.stream(i.spliterator(), false).toList();
        Iterable<Raport> i2 = sM.getSrvMesaje().afisareConversatiiUseriData(MainViewController.getIdLogin(), d1, d2);
        List<Raport> l2 = StreamSupport.stream(i2.spliterator(), false).toList();
        return Stream.concat(l1.parallelStream(), l2.parallelStream()).toList();
    }

    private List<Raport> load2(String firstName, String lastName, LocalDate d1, LocalDate d2) {
        Utilizator u2 = sM.getSrvUtilizator().findAllByName(firstName, lastName).stream().limit(1).toList().get(0);
        List<Raport> rapoarte = new ArrayList<>();
        sM.getSrvMesaje().afisareConversatie(MainViewController.getIdLogin(), u2.getId())
                .forEach(mesajConv -> {
                    if (mesajConv.getDateTime().toLocalDate().compareTo(d1) >= 0 &&
                            mesajConv.getDateTime().toLocalDate().compareTo(d2) <= 0
                            && mesajConv.getFrom().getId().equals(u2.getId()))
                        rapoarte.add(new Raport(mesajConv.getMsg(), mesajConv.getDateTime().toLocalDate()));
                });
        return rapoarte;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableActivity1.setCellValueFactory(new PropertyValueFactory<>("mesaj"));
        tableDate1.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableMessages2.setCellValueFactory(new PropertyValueFactory<>("mesaj"));
        tableDate2.setCellValueFactory(new PropertyValueFactory<>("date"));
        table1.setItems(modelRapoarte1);
        table2.setItems(modelRapoarte2);
    }

    public void onFindButton1(ActionEvent actionEvent) {
        //10/01/2022 - 13/01/2022
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String[] dates = dateActivity.getText().split("-");
        modelRapoarte1.setAll(
                load(LocalDate.parse(dates[0].strip(), formatter), LocalDate.parse(dates[1].strip(), formatter)));
    }

    public void onFindButton2(ActionEvent actionEvent) {
        //10/01/2022 - 13/01/2022
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String[] dates = dateMessages.getText().split("-");
        String[] nume = friendName.getText().split(" ");
        modelRapoarte2.setAll(
                load2(nume[0].strip(), nume[1].strip()
                        , LocalDate.parse(dates[0].strip(), formatter),
                        LocalDate.parse(dates[1].strip(), formatter)));
    }

    private void exportPdf(List<Raport> rapoarte, String filePath) {
        //10/01/2022 - 13/01/2022
        try (PDDocument doc = new PDDocument()) {
            PDFont font = PDType1Font.TIMES_BOLD_ITALIC;
            PDPage page = new PDPage();
            doc.addPage(page);
            PDPageContentStream content = new PDPageContentStream(doc, page);
            content.setFont(font, 14);
            int lines = 1;
            float pageHeight = page.getMediaBox().getHeight();
            for (Raport raport : rapoarte) {
                content.beginText();
                content.newLineAtOffset(20, pageHeight - 15 * lines);
                content.showText(raport.getMesaj() + " la data de " + raport.getDate());
                content.endText();
                ++lines;
            }
            content.close();

            doc.save(filePath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void onExportButton1(ActionEvent actionEvent) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String[] dates = dateActivity.getText().split("-");
        exportPdf(load(LocalDate.parse(dates[0].strip(), formatter), LocalDate.parse(dates[1].strip(), formatter)),
                "C:\\Users\\turtu\\Desktop\\RaportActivitati.pdf");
    }

    public void onExportButton2(ActionEvent actionEvent) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String[] dates = dateMessages.getText().split("-");
        String[] nume = friendName.getText().split(" ");
        exportPdf(
                load2(nume[0].strip(), nume[1].strip()
                        , LocalDate.parse(dates[0].strip(), formatter),
                        LocalDate.parse(dates[1].strip(), formatter)),
                "C:\\Users\\turtu\\Desktop\\RaportMesaje.pdf");
    }

}
