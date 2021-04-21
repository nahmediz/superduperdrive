package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialServices;
import com.udacity.jwdnd.course1.cloudstorage.services.FileServices;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteServices;
import com.udacity.jwdnd.course1.cloudstorage.services.UserServices;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/home")
public class HomeController {

    private FileServices fileServices;
    private NoteServices noteServices;
    private CredentialServices credentialServices;
    private UserServices userServices;

    public HomeController(FileServices fileServices, NoteServices noteServices, UserServices userServices, CredentialServices credentialServices) {
        this.fileServices = fileServices;
        this.noteServices = noteServices;
        this.userServices = userServices;
        this.credentialServices = credentialServices;
    }

    @GetMapping
    public String getHome(@ModelAttribute("file") File file, @ModelAttribute("note") Note note, @ModelAttribute("credential") Credential credential, Model model, Authentication authentication) {
        User user = userServices.getUser(authentication.getName());
        model.addAttribute("files", fileServices.getUserFiles(user));
        model.addAttribute("notes", noteServices.getUserNotes(user));
        model.addAttribute("credentials", credentialServices.getUserCredentials(user));
        return "home";
    }

    @PostMapping("/updateFile")
    public String addFile(@RequestParam("fileUpload") MultipartFile multipartFile, Model model, Authentication authentication) throws IOException {
        User user = userServices.getUser(authentication.getName());
        File file = new File(null, multipartFile.getOriginalFilename(), multipartFile.getContentType(), String.valueOf(multipartFile.getSize()), user.getUserid(), multipartFile.getBytes());

        File dbFile = fileServices.fileExists(file, user);
        if (dbFile == null) {
            int addedRows = fileServices.addFile(file);

            if (addedRows < 0) {
                model.addAttribute("operationError", true);
            } else {
                model.addAttribute("operationSuccess", true);
            }
        } else {
            dbFile.setFilename(multipartFile.getOriginalFilename());
            dbFile.setFilesize(String.valueOf(multipartFile.getSize()));
            dbFile.setFiledata(multipartFile.getBytes());

            fileServices.updateFile(dbFile);

            model.addAttribute("operationSuccess", true);
        }

        model.addAttribute("files", fileServices.getUserFiles(user));
        return "result";
    }

    @GetMapping(value = "/downloadFile/{id}")
    public String downloadFile(@PathVariable(value = "id") int fileId, HttpServletResponse resp, Authentication authentication) throws IOException {
        File file = fileServices.getFile(fileId);
        byte[] content = file.getFiledata();

        resp.setContentType(file.getContenttype());
        resp.setHeader("Content-Disposition", "attachment; filename=" + file.getFilename());
        resp.setContentLength(content.length);

        OutputStream os = resp.getOutputStream();
        try {
            os.write(content, 0, content.length);
        } finally {
            os.close();
        }

        return "result";
    }

    @RequestMapping(value = "/deleteFile/{id}", method = RequestMethod.DELETE)
    public String deleteFile(@PathVariable(value = "id") int fileId, Model model, Authentication authentication) {
        File file = fileServices.getFile(fileId);
        boolean deleteProceeded = fileServices.deleteFile(file);

        if (deleteProceeded) {
            model.addAttribute("operationSuccess", true);
        } else {
            model.addAttribute("operationError", true);
            model.addAttribute("detailedError", true);
            model.addAttribute("detailedErrorText", "Delete operation failed, the file does not exist in the database.");
        }

        return "result";
    }

    @PostMapping("/updateNote")
    public String addNote(@ModelAttribute("note") Note note, Model model, Authentication authentication) {
        User user = userServices.getUser(authentication.getName());
        note.setUserid(user.getUserid());

        if (note.getNoteid() == null) {
            int addedRows = noteServices.addNote(note);

            if (addedRows < 0) {
                model.addAttribute("operationError", true);
            } else {
                model.addAttribute("operationSuccess", true);
            }
        } else {
            noteServices.updateNote(note);

            model.addAttribute("operationSuccess", true);
        }

        model.addAttribute("notes", noteServices.getUserNotes(user));
        return "result";
    }

    @RequestMapping(value = "/deleteNote/{id}", method = RequestMethod.DELETE)
    public String deleteNote(@PathVariable(value = "id") int noteId, Model model, Authentication authentication) {
        Note note = noteServices.getNote(noteId);
        boolean deleteProceeded = noteServices.deleteNote(note);

        if (deleteProceeded) {
            model.addAttribute("operationSuccess", true);
        } else {
            model.addAttribute("operationError", true);
            model.addAttribute("detailedError", true);
            model.addAttribute("detailedErrorText", "Delete operation failed, the note does not exist in the database.");
        }

        return "result";
    }

    @PostMapping("/updateCredential")
    public String addCredential(@ModelAttribute("credential") Credential credential, Model model, Authentication authentication) {
        User user = userServices.getUser(authentication.getName());
        credential.setUserid(user.getUserid());

        if (credential.getCredentialId() == null) {
            int addedRows = credentialServices.addCredential(credential);

            if (addedRows < 0) {
                model.addAttribute("operationError", true);
            } else {
                model.addAttribute("operationSuccess", true);
            }
        } else {
            credentialServices.updateCredential(credential);

            model.addAttribute("operationSuccess", true);
        }

        model.addAttribute("credentials", credentialServices.getUserCredentials(user));
        return "result";
    }

    @RequestMapping(value = "/deleteCredential/{id}", method = RequestMethod.DELETE)
    public String deleteCredential(@PathVariable(value = "id") int credentialId, Model model, Authentication authentication) {
        Credential credential = credentialServices.getCredential(credentialId);
        boolean deleteProceeded = credentialServices.deleteCredential(credential);

        if (deleteProceeded) {
            model.addAttribute("operationSuccess", true);
        } else {
            model.addAttribute("operationError", true);
            model.addAttribute("detailedError", true);
            model.addAttribute("detailedErrorText", "Delete operation failed, the credential does not exist in the database.");
        }

        return "result";
    }
}
