package com.tim_wro.skupstina.services;


import com.marklogic.client.DatabaseClient;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.InputStreamHandle;
import com.tim_wro.skupstina.model.Akt;
import com.tim_wro.skupstina.model.Amandman;
<<<<<<< HEAD
import com.tim_wro.skupstina.model.StavkaAmandmana;
import com.tim_wro.skupstina.model.TipIzmene;
=======
import com.tim_wro.skupstina.model.Sednica;
>>>>>>> 5310968a9c8066b9c5e3ebf53dbf4135f7adc791
import com.tim_wro.skupstina.util.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AmandmanService {

<<<<<<< HEAD
=======
    @Autowired
    private SednicaService sednicaService;
>>>>>>> 5310968a9c8066b9c5e3ebf53dbf4135f7adc791

    public void writeInMarkLogicDB(File file, String id) throws FileNotFoundException {
        DatabaseClient client = Connection.getConnection();

        // Create a document manager to work with XML files.
        XMLDocumentManager xmlManager = client.newXMLDocumentManager();

        String docId = "/amandman/" + id + ".xml";

        // Create an input stream handle to hold XML content.
        InputStreamHandle handle = new InputStreamHandle(new FileInputStream(file));

        // Write the document to the database
        System.out.println("[INFO] Inserting \"" + docId + "\" to \" database.");
        xmlManager.write(docId, handle);

        // Release the client
        client.release();
    }

<<<<<<< HEAD
    public void applyAmandman(Amandman amd, Akt akt){
        for(StavkaAmandmana stavka : amd.getStavke().getStavkaAmandmana()){
            if(stavka.getTipIzmene() == TipIzmene.BRISANJE){

            }
            else if(stavka.getTipIzmene() == TipIzmene.DODAVANJE){
                System.out.println("");
            }
            else if(stavka.getTipIzmene() == TipIzmene.IZMENA){

            }

        }
=======
    public List<Amandman> getBySednicaRedniBroj(String id) throws JAXBException {

        Sednica sednica = sednicaService.findById(id);
        System.out.println("glupa sednica " + sednica.toString());

        List<Akt> aktiSednice = sednica.getAkt();

        List<Amandman> amandmaniOdSednice = new ArrayList<>();

        for(Akt a : aktiSednice){
            amandmaniOdSednice.addAll(a.getAmandman());
        }

        System.out.println("amandmani od te sednice " + amandmaniOdSednice);

        return amandmaniOdSednice;
>>>>>>> 5310968a9c8066b9c5e3ebf53dbf4135f7adc791
    }
}
