package com.ericsson.ei.frontend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SubscriptionFilesController {
	
	@Value("${ei.subscriptionFilePath}") private String subscriptionFilePath;
    private static final String APPLICATION_JSON = "application/json";

    
    @RequestMapping(value = "/download/subscriptiontemplate", method = RequestMethod.GET, produces = APPLICATION_JSON)
    public @ResponseBody void downloadA(HttpServletResponse response) throws IOException {
        File file = getFile(subscriptionFilePath);
        InputStream in = new FileInputStream(file);

        response.setContentType(APPLICATION_JSON);
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
        response.setHeader("Content-Length", String.valueOf(file.length()));
        FileCopyUtils.copy(in, response.getOutputStream());
    }

    
    private File getFile(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        if (!file.exists()){
            throw new FileNotFoundException("File " + '"' + filePath + '"' + " was not found.");
        }
        return file;
    }
}
