package com.techweblearn;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class DownloadingThread implements Runnable {


    private static final int BUFFER_SIZE=8192;

    private ByteBuffer byteBuffer;
    private long downloaded=0;
    private boolean pause=false;
    private HttpURLConnection httpURLConnection;
    private ReadableByteChannel readableByteChannel;
    private WritableByteChannel writableByteChannel;
    private FileOutputStream fileOutputStream;
    int responseCode;



    private DownloadTask downloadTask;
    private int partNo;
    private PartDownloadListener partDownloadListener;

    public DownloadingThread(DownloadTask downloadTask, PartDownloadListener partDownloadListener) {
        this.downloadTask = downloadTask;
        this.partDownloadListener = partDownloadListener;
    }




    public DownloadingThread(DownloadTask downloadTask, int partNo, PartDownloadListener partDownloadListener) {
        this.downloadTask = downloadTask;
        this.partNo = partNo;
        this.partDownloadListener = partDownloadListener;
    }

    @Override
    public void run() {

        try {
            URL url=new URL(downloadTask.url);
             httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.addRequestProperty("Range",downloadTask.getRange());
            httpURLConnection.setConnectTimeout(30000);
            httpURLConnection.connect();
            responseCode=httpURLConnection.getResponseCode();
            System.out.println(httpURLConnection.getHeaderFields().toString());
            byteBuffer=ByteBuffer.allocate(BUFFER_SIZE);
            readableByteChannel=Channels.newChannel(httpURLConnection.getInputStream());
            fileOutputStream=new FileOutputStream(new File(downloadTask.getFilename()),true);
            writableByteChannel=fileOutputStream.getChannel();

            while (true)
            {
                byteBuffer.clear();
               /* if(pause)
                {
                    break;
                }*/

                int read=readableByteChannel.read(byteBuffer);
                byteBuffer.flip();
                writableByteChannel.write(byteBuffer);
                if(read==-1)
                    break;

                downloaded+=read;
                partDownloadListener.update(downloaded,partNo);

            }



            byteBuffer.clear();
            fileOutputStream.close();
            writableByteChannel.close();
            readableByteChannel.close();
            httpURLConnection.disconnect();

            if(pause)
            partDownloadListener.pause(partNo,downloaded);
            else partDownloadListener.completed(partNo);

        }
        catch (ClosedChannelException pause)
        {
            partDownloadListener.pause(partNo,downloaded);
            pause.printStackTrace();
        }
        catch (ConnectException c)
        {
            c.printStackTrace();
            partDownloadListener.error(partNo);

        }catch (IOException e) {
            e.printStackTrace();
            partDownloadListener.error(responseCode,e.getLocalizedMessage(),partNo);

        }


    }

    public void pause()
    {
        pause=true;
        try {
            byteBuffer.clear();
            fileOutputStream.close();
            writableByteChannel.close();
            readableByteChannel.close();
            httpURLConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
