package com.techweblearn.Utils;

import com.techweblearn.DownloadTask;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DeleteFiles {

    public static void delete(DownloadTask[]downloads)
    {
        for(DownloadTask downloadTask:downloads)
        {
            Path path=Paths.get(downloadTask.getFilename());
            try {
                Files.delete(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
