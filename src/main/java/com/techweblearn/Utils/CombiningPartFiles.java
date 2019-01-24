package com.techweblearn.Utils;

import com.techweblearn.CombiningFileListener;
import com.techweblearn.DownloadListener;
import com.techweblearn.DownloadTask;

import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.*;

public class CombiningPartFiles {

    public static void combineFiles(File output, DownloadTask[] downloadTasks, CombiningFileListener combiningFileListener)
    {
        int read;
        int total_read = 0;

        if(!output.exists())
        {
            try {
                output.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ByteBuffer byteBuffer=ByteBuffer.allocate(8192);
        Path outFile=Paths.get(output.toURI());

        try(FileChannel outChannel=FileChannel.open(outFile,WRITE,APPEND,CREATE)) {

            System.out.println("Combining Files");
            for(int i=0;i<downloadTasks.length;i++)
            {


                Path inFile=Paths.get(downloadTasks[i].getFilename());
                try ( FileChannel inFileChannel=FileChannel.open(inFile,READ)){

                    while (true)
                    {
                        byteBuffer.clear();
                        read=inFileChannel.read(byteBuffer);
                        byteBuffer.flip();
                        outChannel.write(byteBuffer);
                        if(read==-1)
                            break;
                         total_read+=read;

                    }




                } catch (IOException e) {
                    combiningFileListener.combineError();
                    e.printStackTrace();
                }




            }



        } catch (IOException e) {
            e.printStackTrace();
        }


        combiningFileListener.combineCompleted();

    }
}
