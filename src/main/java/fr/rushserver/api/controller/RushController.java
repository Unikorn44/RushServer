package fr.rushserver.api.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fr.rushserver.transverse.exception.NoRushFileException;

@RestController
public class RushController {

    public static final String RUSH_ROOT_DIR = "C:\\Code\\Java\\";
    public static final String DATA_RUSH_ROOT_DIR = "C:\\Code\\Java\\data\\";

    //recherche fichiers XMLRush
    @GetMapping("/rushs")
    public ResponseEntity<List<String>> getRushs() throws NoRushFileException {
        File directory = new File(RUSH_ROOT_DIR);
        List<File> rushFiles = Arrays.stream(
                Optional.ofNullable(directory.listFiles())
                        .orElseThrow(NoRushFileException::new))
                .filter(f -> f.getName().startsWith("XMLRush"))
                .toList();
        return new ResponseEntity<>(
                rushFiles.stream().map(File::getName).toList(), HttpStatus.OK);
    }

    //lecture contenu fichiers XMLRush
    @GetMapping("/rushs/{name}/content")
    public ResponseEntity<Resource> getRushs(@PathVariable(name = "name",required = true) String name) throws NoRushFileException {
        File file = new File(RUSH_ROOT_DIR + name);
        InputStreamResource resource = null;
        try {
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new NoRushFileException(e);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_JSON)
                .body(resource);
    }

    //lecture contenu fichiers XMLRush
    @GetMapping("/rushs/{name}/files/{fileName}")
    public ResponseEntity<Resource> getFiles(
            @PathVariable(name = "name",required = true) String name,
            @PathVariable(name = "fileName",required = true) String fileName) throws NoRushFileException {
        File file = new File(DATA_RUSH_ROOT_DIR + name.split("\\.")[0], fileName);
        InputStreamResource resource = null;
        try {
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new NoRushFileException(e);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        MediaType mediaType = null;
        if (fileName.endsWith("xml")) {
            mediaType = MediaType.APPLICATION_XML;
        } else {
            mediaType = MediaType.APPLICATION_JSON;
        }

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(mediaType)
                .body(resource);
    }
}
