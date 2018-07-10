package com.ymy.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import sun.misc.BASE64Encoder;

import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.List;

public class QRGenUtils {

    private static final int black = 0xFF000000;
    private static final int white  = 0xFFFFFFFF;

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? black : white);
            }
        }
        return image;
    }


    public static void writeToFile(BitMatrix matrix, String format, File file)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        ImageIO.write(image, format, file);
    }

    public static void createQRImage(String content, int width, int height, String path, String fileName) throws Exception {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        if (StringUtils.isNotBlank(path)) {
            if (!path.endsWith("/")) {
                path = path + "/";
            }
        } else {
            path = "";
        }
        String suffix = "jpg";
        if (fileName.indexOf(".") <= -1) {
            fileName = fileName + "." + suffix;
        } else {
            suffix = fileName.split("[.]")[1];
        }
        fileName = path + fileName;
        File file = new File(fileName);
        writeToFile(bitMatrix, suffix, file);
    }


    public static BufferedImage createQRImageBuffer(String content, int width, int height) throws  Exception{
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        BufferedImage image = toBufferedImage(bitMatrix);
        return image;
    }
    
    /**
     * @Description: 根据图片地址转换为base64编码字符串
     * @Author: 
     * @CreateTime: 
     * @return
     */
    public static String getImageStr(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }
    
    /**
     * @Description: 由dataList生成二维码列表
     * @Author: 
     * @CreateTime: 
     * @return
     */
    public static StringBuffer getPictureList(List<Object[]> dataList) {
    	
    	StringBuffer buffer=new StringBuffer();
    	if(dataList.size()%3==0){
		for (int i = 0; i < dataList.size(); i+=3) {
			buffer.append(
					"<tr>"
					+"<td style='text-align:center' width='250px'>"
					    +"<div>"
							+"<img  src='data:img/png;base64,"
							+dataList.get(i)[1]			
						+"' /></div>"
						+"<div>"
							+dataList.get(i)[2]
						+"</div>"
						+"<div>"
						+"<img height='20' width='20' src='data:img/png;base64,"
							+dataList.get(i)[3]
						+"' /></div>"
					+"</td>"
					+"<td style='text-align:center' width='250px'>"
						+"<div>"
							+"<img  src='data:img/png;base64,"
							+dataList.get(i+1)[1]		
						+"' /></div>"
						+"<div>"
							+dataList.get(i+1)[2]
						+"</div>"
						+"<div>"
						+"<img height='20' width='20' src='data:img/png;base64,"
							+dataList.get(i+1)[3]
					    +"' /></div>"
					+"</td>"
					+"<td style='text-align:center' width='250px'>"
						+"<div>"
							+"<img  src='data:img/png;base64,"
							+dataList.get(i+2)[1]		
						+"' /></div>"
						+"<div>"
							+dataList.get(i+2)[2]
						+"</div>"
						+"<div>"
							+"<img height='20' width='20' src='data:img/png;base64,"
							+dataList.get(i+2)[3]
						+"' /></div>"
					+"</td>"
				+"</tr>");
		}
    	}else if(dataList.size()%3==1){
    		for (int i = 0; i < (dataList.size()/3)*3; i+=3) {
    			buffer.append(
    					"<tr>"
    					+"<td style='text-align:center' width='250px'>"
    					    +"<div>"
    							+"<img  src='data:img/png;base64,"
    							+dataList.get(i)[1]			
    						+"' /></div>"
    						+"<div>"
    							+dataList.get(i)[2]
    						+"</div>"
    						+"<div>"
    						+"<img height='20' width='20' src='data:img/png;base64,"
    							+dataList.get(i)[3]
    						+"' /></div>"
    					+"</td>"
    					+"<td style='text-align:center' width='250px'>"
    						+"<div>"
    							+"<img  src='data:img/png;base64,"
    							+dataList.get(i+1)[1]		
    						+"' /></div>"
    						+"<div>"
    							+dataList.get(i+1)[2]
    						+"</div>"
    						+"<div>"
    						+"<img height='20' width='20' src='data:img/png;base64,"
    							+dataList.get(i+1)[3]
    					    +"' /></div>"
    					+"</td>"
    					+"<td style='text-align:center' width='250px'>"
    						+"<div>"
    							+"<img  src='data:img/png;base64,"
    							+dataList.get(i+2)[1]		
    						+"' /></div>"
    						+"<div>"
    							+dataList.get(i+2)[2]
    						+"</div>"
    						+"<div>"
    							+"<img height='20' width='20' src='data:img/png;base64,"
    							+dataList.get(i+2)[3]
    						+"' /></div>"
    					+"</td>"
    				+"</tr>");
    	}
    		buffer.append(
					"<tr>"
					+"<td style='text-align:center' width='250px'>"
					    +"<div>"
							+"<img  src='data:img/png;base64,"
							+dataList.get(dataList.size()-1)[1]			
						+"' /></div>"
						+"<div>"
							+dataList.get(dataList.size()-1)[2]
						+"</div>"
						+"<div>"
						+"<img height='20' width='20' src='data:img/png;base64,"
							+dataList.get(dataList.size()-1)[3]
						+"' /></div>"
					+"</td>"
					+"<td style='text-align:center' width='250px'>"
						
					+"</td>"
					+"<td style='text-align:center' width='250px'>"
					
					+"</td>"
				+"</tr>");
    	}else if(dataList.size()%3==2){
    		for (int i = 0; i < (dataList.size()/3)*3; i+=3) {
    			buffer.append(
    					"<tr>"
    					+"<td style='text-align:center' width='250px'>"
    					    +"<div>"
    							+"<img  src='data:img/png;base64,"
    							+dataList.get(i)[1]			
    						+"' /></div>"
    						+"<div>"
    							+dataList.get(i)[2]
    						+"</div>"
    						+"<div>"
    						+"<img height='20' width='20' src='data:img/png;base64,"
    							+dataList.get(i)[3]
    						+"' /></div>"
    					+"</td>"
    					+"<td style='text-align:center' width='250px'>"
    						+"<div>"
    							+"<img  src='data:img/png;base64,"
    							+dataList.get(i+1)[1]		
    						+"' /></div>"
    						+"<div>"
    							+dataList.get(i+1)[2]
    						+"</div>"
    						+"<div>"
    						+"<img height='20' width='20' src='data:img/png;base64,"
    							+dataList.get(i+1)[3]
    					    +"' /></div>"
    					+"</td>"
    					+"<td style='text-align:center' width='250px'>"
    						+"<div>"
    							+"<img  src='data:img/png;base64,"
    							+dataList.get(i+2)[1]		
    						+"' /></div>"
    						+"<div>"
    							+dataList.get(i+2)[2]
    						+"</div>"
    						+"<div>"
    							+"<img height='20' width='20' src='data:img/png;base64,"
    							+dataList.get(i+2)[3]
    						+"' /></div>"
    					+"</td>"
    				+"</tr>");
    	}
    		buffer.append(
					"<tr>"
					+"<td style='text-align:center' width='250px'>"
					    +"<div>"
							+"<img  src='data:img/png;base64,"
							+dataList.get(dataList.size()-2)[1]			
						+"' /></div>"
						+"<div>"
							+dataList.get(dataList.size()-2)[2]
						+"</div>"
						+"<div>"
						+"<img height='20' width='20' src='data:img/png;base64,"
							+dataList.get(dataList.size()-2)[3]
						+"' /></div>"
					+"</td>"
					+"<td style='text-align:center' width='250px'>"
						+"<div>"
							+"<img  src='data:img/png;base64,"
							+dataList.get(dataList.size()-1)[1]			
						+"' /></div>"
						+"<div>"
							+dataList.get(dataList.size()-1)[2]
						+"</div>"
						+"<div>"
							+"<img height='20' width='20' src='data:img/png;base64,"
							+dataList.get(dataList.size()-1)[3]
						+"' /></div>"
					+"</td>"
					+"<td style='text-align:center' width='250px'>"
					
					+"</td>"
				+"</tr>");
    	}
    	return buffer;
    }
    
    
    /**
     * @Description: 由dataList生成数据列表
     * @Author: 
     * @CreateTime: 
     * @return
     */
    public static StringBuffer getDataList(List<Object[]> dataList) {
    	StringBuffer buffer=new StringBuffer();
    	for (int i = 0; i < dataList.size(); i++) {
			buffer.append(
					"<tr>"
					+"<td style='text-align:center' width='250px'>"
						+dataList.get(i)[0]
					+"</td>"
					+"<td style='text-align:center' width='250px'>"
						+dataList.get(i)[1]
					+"</td>"
					+"<td style='text-align:center' width='250px'>"
						+dataList.get(i)[2]
					+"</td>"
				+"</tr>");
		}
    	return buffer;
    }
    
}