package com.rouge.dev.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Cretated By Rohan Rawal on 21-01-2020
 */
@RestController
@RequestMapping("/json")
@Slf4j
public class JsonController {

    @PostMapping(value = "/validate",produces= "application/json")
    public Map<String, Object> validateJson(String normalJsonString){

        Map<String, Object> response = new HashMap<>();
        try {

            ObjectMapper objectMapper =new ObjectMapper();

            objectMapper.readTree(normalJsonString);

            response.put("isValidJson", "The Following String is a Valid Json");

            response.put("success",true);

        }catch (Exception e){

            response.put("isValidJson","The Following String is Not a Valid Json");

            response.put("success",false);

            return response;

        }
        return  response;
    }

    @PostMapping(value = "/beautify",produces = "application/json")
    public Map<String,Object> beautifyJson(String normalJsonString){

        Map<String, Object> response = new HashMap<>();
        try{
            log.debug("Beautify json meathod started ");

            ObjectMapper objectMapper =new ObjectMapper();

            Object object = objectMapper.readValue(normalJsonString,Object.class);

            ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();

            String formattedJsonString = objectWriter.writeValueAsString(object);

            response.put("success",true);

            response.put("formattedJsonString", formattedJsonString);

        }catch (Exception e){

            log.debug("Beautify json meathod caught exception :: "+e.getMessage());

            response.put("success",false);

            response.put("formattedJsonString", e.getMessage());
        }

        return response;
    }

    @PostMapping(value = "/minify" , produces = "application/json")
    public Map<String,Object> minifyJson(String normalJsonString){

        Map<String,Object> response = new HashMap<>();

        try{

            log.debug("Minify json meathod started ");

            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode jsonNode =objectMapper.readValue(normalJsonString,JsonNode.class);

            response.put("formattedJsonString",jsonNode.toString());

            response.put("success",true);

        }catch (Exception e){

            log.debug("Minify json meathod caught exception :: "+e.getMessage());

            response.put("success",false);

            response.put("formattedJsonString", e.getMessage());
        }

        return response;
    }

    /**
     *
     * @param normalJsonString
     * @return response
     */
    @PostMapping(value = "/convert/xml",produces = "application/json")
    public Map<String,Object> convertToXml(String normalJsonString){

        Map<String,Object> response = new HashMap<>();

        try{
            log.debug("convertToXml json meathod started ");

            JSONObject jsonObject = new JSONObject(normalJsonString);

            String xmlData = XML.toString(jsonObject);

            xmlData = beautifyXml(xmlData,2);

            if(!StringUtils.isEmpty(xmlData)){

                response.put("xmlData",xmlData);

                response.put("success",true);
            }else {

                throw new Exception("xmlData beautifier is null");

            }
        } catch (Exception e){
            log.debug("Minify json meathod caught exception :: "+e.getMessage());
            response.put("xmlData",e.getMessage());
            response.put("success",false);
        }

        return response;
    }

    /**
     *
     * @param xmlData
     * @param indent
     * @return
     * @throws Exception
     */
    public String beautifyXml (String xmlData , int indent) throws Exception{

        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        transformerFactory.setAttribute("indent-number",indent);

        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT,"yes");

        StringWriter stringWriter = new StringWriter();

        StreamResult xmlOutput = new StreamResult(stringWriter);

        Source xmlInput = new StreamSource(new StringReader(xmlData));

        transformer.transform(xmlInput,xmlOutput);

        return  xmlOutput.getWriter().toString();

    }

    @PostMapping(value = "/convert/csv" , produces = "application/json")
    public Map<String,Object> convertToCsv(String normalJsonString){

        Map<String,Object> response = new HashMap<>();

        try{
            log.debug("convertToCsv json meathod started");

            JsonNode jsonTree = new ObjectMapper().readTree(normalJsonString);

            CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();

            JsonNode firstObject = jsonTree.elements().next();

            firstObject.fieldNames().forEachRemaining(fieldName -> {csvSchemaBuilder.addColumn(fieldName);} );

            CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();

            CsvMapper csvMapper = new CsvMapper();
            String csvData = csvMapper.writerFor(JsonNode.class)
                    .with(csvSchema)
                    .writeValueAsString(firstObject);

            response.put("csvData",csvData);

            response.put("success",true);

           }catch (Exception e){

            log.debug("Minify json meathod caught exception :: "+e.getMessage());
            response.put("csvData",e.getMessage());
            response.put("success",false);
        }

        return response;
    }

    @PostMapping(value = "/validateUser" , produces = "application/json")
    public Map<String,Object> validateUser(String username, String password){

        Map<String,Object> response = new HashMap<>();

        try{
            log.debug("validateUser meathod is started");
            if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)){
                if ((username.equalsIgnoreCase( "user") && password.equalsIgnoreCase("user"))|| (username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin"))){

                    response.put("success",true);
                }
            }else {
                response.put("success",true);
            }

        }catch (Exception e){
            log.debug("validateUser meathod is caught error");
            response.put("success",true);
        }
        return response;
    }
}
