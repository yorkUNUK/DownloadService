package com.example.yorkDownlaod.download;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.OutputStream;

/**
 * @Author York
 * @Date 2022/5/7 5:18 下午
 * @Version 1.0
 */

@RestController
public class download {
    @Value("${storage.url}")
    private String url;
    @Value("${file.name}")
    private String fileName;

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void receiveMessage(HttpServletResponse response){
        String file = url;
        try {
            FileInputStream inputStream = new FileInputStream(file);
            byte[] data = new byte[inputStream.available()];
            //inputStream.read(data);
            String diskFileName = fileName;
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode(diskFileName, "UTF-8") + "\"");
            response.setContentLength(data.length);
            response.setHeader("Accept-Ranges", "bytes");

            int read = 0;
            OutputStream os = response.getOutputStream();
            while((read = inputStream.read(data))!=-1){//按字节逐个写入，避免内存占用过高
                os.write(data, 0, read);
            }
            os.flush();
            os.close();
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
