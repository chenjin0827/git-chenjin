package com.chenjin.common.web.util;

import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.StringWriter;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XmlFormat
{
    public static String format(String str)
    {
        SAXReader reader = new SAXReader();

        StringReader in = new StringReader(str);
        try
        {
            Document doc = reader.read(in);

            OutputFormat formater = OutputFormat.createPrettyPrint();

            formater.setEncoding("utf-8");

            StringWriter out = new StringWriter();

            XMLWriter writer = new XMLWriter(out, formater);

            writer.write(doc);

            writer.close();

            return out.toString();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception
    {
        String str = "<?xml version=\"1.0\" encoding=\"GBK\"?><RequestData><HeadData><UserCode>sh1_admin</UserCode><UserName>sh1_admin</UserName><UserCompanyCode>3107</UserCompanyCode><UserCompanyName>上海分公司一部</UserCompanyName><RequestType>03</RequestType></HeadData><BodyData><ReportId>113100000033</ReportId><Insurant>a5rfg87</Insurant><NumberPlate>沪E78612</NumberPlate><EngineModel></EngineModel><CarVin></CarVin><AccidentDate>2011-02-25 15:07:00</AccidentDate><ReportDate>2011-02-25 15:07:00</ReportDate><Province>310000</Province><City>310100</City><District></District><AccidentPlace>1</AccidentPlace><AccidentLongitude></AccidentLongitude><AccidentLatitude></AccidentLatitude><SurveyLongitude></SurveyLongitude><SurveyLatitude></SurveyLatitude><SceneReportFlag></SceneReportFlag><Reporter></Reporter><ReporterTel></ReporterTel><SurveyPlace></SurveyPlace><OperatorId>3525</OperatorId><OperatorName>sh_admin</OperatorName><ReportDealId>30000800</ReportDealId><ReportDealName>江苏分公司</ReportDealName><CompanyName></CompanyName><CustomerTypeCode></CustomerTypeCode><ForcePolicyId>a5rfg87a5rfg87a5rfg87</ForcePolicyId><BizPolicyId></BizPolicyId><Index>0</Index><FieldName>5</FieldName></BodyData></RequestData>";
        System.out.println(format(str));
        format(str);
    }
}