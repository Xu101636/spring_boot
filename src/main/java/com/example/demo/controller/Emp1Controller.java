package com.example.demo.controller;

import com.example.demo.bean.Emp1;
import com.example.demo.service.Emp1Service;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/emp")
public class Emp1Controller {

    @Resource
    private Emp1Service service;

    @RequestMapping("/list")
    public String list(Model model){
        List<Emp1> emplist = service.selemps();
        System.out.println("list size "+emplist.size());
        model.addAttribute("emplist",emplist);
        return "emplist";
    }

    @RequestMapping("/excelinput")
    public String excelinput(MultipartFile ff){
        //创建临时file对象
        File file= null;
        try {
            file = File.createTempFile("tmp", null);
            //把MultipartFile对象转换成java.io.FileUI对象
            ff.transferTo(file);
            InputStream inputStream = new FileInputStream(file);
            HSSFWorkbook  workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            //循环行()
            for (int i=1;i<sheet.getLastRowNum();i++){
                HSSFRow row = sheet.getRow(i);
                Emp1 emp = new Emp1();
                //姓名，中文
                emp.setEname(row.getCell(0).getStringCellValue());

                //密码，存数字的单元格不能用getStringCellValue()获取因为单元格格式是numeric
                row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println(row.getCell(1).getStringCellValue());
                emp.setPwd(row.getCell(1).getStringCellValue());
                emp.setSex(row.getCell(2).getStringCellValue());

                row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                emp.setAge(Integer.parseInt(row.getCell(3).getStringCellValue()));

                row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                emp.setIdcard(row.getCell(4).getStringCellValue());

                row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                emp.setTel(row.getCell(5).getStringCellValue());
                emp.setBirth(row.getCell(6).getDateCellValue());
                emp.setFace(row.getCell(7).getStringCellValue());

                row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
                emp.setDid(Integer.parseInt(row.getCell(8).getStringCellValue()));
                //一行数据做一次添加
                service.saveemp(emp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:list";
    }

    //Excel导出：查询出需要导出的数据，并且用行列方式创建Excel
    @RequestMapping("/exportemp")
    public void exportemp(HttpServletResponse res){
        //查询所有数据
        List<Emp1> list = service.selemps();

        //创建Excel对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建工作表
        HSSFSheet sheet = workbook.createSheet();
        //第一行 标题
        HSSFRow row0 = sheet.createRow(0);
        HSSFCell cell00 = row0.createCell(0);
        cell00.setCellValue("信息表");

        //第二行 表头
        HSSFRow row1 = sheet.createRow(1);
        HSSFCell cell10 = row1.createCell(0);
        cell10.setCellValue("编号");

        HSSFCell cell11 = row1.createCell(1);
        cell11.setCellValue("姓名");

        HSSFCell cell12 = row1.createCell(2);
        cell12.setCellValue("性别");

        HSSFCell cell13 = row1.createCell(3);
        cell13.setCellValue("年龄");

        HSSFCell cell14 = row1.createCell(4);
        cell14.setCellValue("身份证");

        HSSFCell cell15 = row1.createCell(5);
        cell15.setCellValue("电话");

        HSSFCell cell16 = row1.createCell(6);
        cell16.setCellValue("出生");

        HSSFCell cell17 = row1.createCell(7);
        cell17.setCellValue("部门编号");

        //循环行
        for (int i=2;i<list.size()+2;i++){
            Emp1 emp = list.get(i-2);
            //创建数据行
            HSSFRow row = sheet.createRow(i);
            HSSFCell cell0 = row.createCell(0);
            cell0.setCellValue(emp.getId());

            HSSFCell cell1 = row.createCell(1);
            cell1.setCellValue(emp.getEname());

            HSSFCell cell2 = row.createCell(2);
            cell2.setCellValue(emp.getSex());

            HSSFCell cell3 = row.createCell(3);
            cell3.setCellValue(emp.getAge());

            HSSFCell cell4 = row.createCell(4);
            cell4.setCellValue(emp.getIdcard());

            HSSFCell cell5 = row.createCell(5);
            cell5.setCellValue(emp.getTel());

            HSSFCell cell6 = row.createCell(6);
            if(emp.getBirth()==null){
                cell6.setCellValue("");
            }else{
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                String birth = sf.format(emp.getBirth());
                cell6.setCellValue(birth);
            }


            HSSFCell cell7 = row.createCell(7);
            cell7.setCellValue(emp.getDid());
        }
        //合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,7));
        //标题样式
        HSSFCellStyle styletitle = workbook.createCellStyle();
        styletitle.setAlignment(HorizontalAlignment.CENTER);
        styletitle.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFFont fonttitle = workbook.createFont();
        fonttitle.setFontHeightInPoints((short)20);
        fonttitle.setBold(true);
        styletitle.setFont(fonttitle);
        cell00.setCellStyle(styletitle);

        //行高
        row0.setHeightInPoints((float) 50);
        row1.setHeightInPoints((float) 20);
        //表头
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFFont font2 = workbook.createFont();
        font2.setBold(true);
        style2.setFont(font2);
        cell10.setCellStyle(style2);
        cell11.setCellStyle(style2);
        cell12.setCellStyle(style2);
        cell13.setCellStyle(style2);
        cell14.setCellStyle(style2);
        cell15.setCellStyle(style2);
        cell16.setCellStyle(style2);
        cell17.setCellStyle(style2);

        //调整部分列宽
        sheet.setColumnWidth(4,(int)35.7*150);
        sheet.setColumnWidth(5,(int)35.7*120);
        sheet.setColumnWidth(6,(int)35.7*100);


        // 将excel的数据写入文件
        ByteArrayOutputStream fos = null;
        byte[] retArr = null;
        try {
            fos = new ByteArrayOutputStream();
            workbook.write(fos);
            retArr = fos.toByteArray();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        OutputStream os = null;
        try {
            os = res.getOutputStream();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            res.reset();
            res.setHeader("Content-Disposition",
                    "attachment; filename=emp_list.xls");// 要保存的文件名
            res.setContentType("application/octet-stream; charset=utf-8");
            os.write(retArr);
            os.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

}
