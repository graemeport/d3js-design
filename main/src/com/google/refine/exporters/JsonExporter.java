/*

Copyright 2010, Google Inc.
Copyright 2012, Port Nason Consulting
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are
met:

    * Redistributions of source code must retain the above copyright
notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above
copyright notice, this list of conditions and the following disclaimer
in the documentation and/or other materials provided with the
distribution.
    * Neither the name of Google Inc. nor the names of its
contributors may be used to endorse or promote products derived from
this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,           
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY           
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package com.google.refine.exporters;

import java.io.IOException;
import java.io.Writer;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;

import org.apache.commons.lang.StringEscapeUtils;

import com.google.refine.ProjectManager;
import com.google.refine.browsing.Engine;
import com.google.refine.model.Project;

public class JsonExporter implements WriterExporter {

    @Override
    public String getContentType() {
        return "application/json";
    }

    @Override
    public void export(final Project project, Properties params, Engine engine, final Writer writer)
        throws IOException {
        
        TabularSerializer serializer = new TabularSerializer() {
            int rowCount = 0;
            
            @Override
            public void startFile(JSONObject options) {
                try {
                    writer.write("{ \"data\" : [\n");
                } catch (IOException e) {
                    // Ignore
                }
            }

            @Override
            public void endFile() {
                try {
                    writer.write("\n] }\n");
                } catch (IOException e) {
                    // Ignore
                }
            }

            @Override
            public void addRow(List<CellData> cells, boolean isHeader) {
                try {
                    String trailingSep = "";
                    
                    if (!isHeader) {
                        if (rowCount++ > 0) {
                            writer.write(",\n");
                        }
                        
                        writer.write("\t{\n");
                        for (CellData cellData : cells) {
                            if (cellData != null && cellData.text != null && cellData.value != null) {
                                Object v = cellData.value;
                                
                                writer.write(trailingSep);
                                writer.write("\t\t\"");
                                writer.write(cellData.columnName);
                                writer.write("\" : ");
                                
                                if (v instanceof String)
                                    writer.write(JSONObject.quote(cellData.text));
                                else if (v instanceof Date || v instanceof Calendar) {
                                    writer.write("\"");
                                    writer.write(cellData.text);
                                    writer.write("\"");
                                } else  
                                    writer.write(cellData.text);
                            }
                            trailingSep = ",\n";
                        }
                        writer.write("\n\t}");
                    }
                    
                } catch (IOException e) {
                    // Ignore
                }
            }
        };
        
        CustomizableTabularExporterUtilities.exportRows(
                project, engine, params, serializer);
    }
}
