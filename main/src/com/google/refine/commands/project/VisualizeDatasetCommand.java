/*

Copyright 2010, Google Inc.
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

package com.google.refine.commands.project;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Properties;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tools.tar.TarOutputStream;
import org.json.JSONException;
import org.json.JSONWriter;
import org.json.JSONObject;

import com.google.refine.ProjectManager;
import com.google.refine.commands.Command;
import com.google.refine.commands.HttpUtilities;
import com.google.refine.model.Project;
import com.google.refine.util.ParsingUtilities;
import com.google.refine.visualize.VisualizeManager;

public class VisualizeDatasetCommand extends Command {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            
        Properties parameters = ParsingUtilities.parseUrlParameters(request);
        String subCommand = parameters.getProperty("subCommand");
        if ("get-available-charts".equals(subCommand)) {
            doGetAvailableCharts(request, response, parameters);
        } else if ("get-selected-chart".equals(subCommand)) {
            doGetSelectedChart(request, response, parameters);
        } else if ("update-display".equals(subCommand)) {
            HttpUtilities.respond(response, "error", "No such sub command");
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        /*
         * The uploaded file is in the POST body as a "file part". If
         * we call request.getParameter() then the POST body will get
         * read and we won't have a chance to parse the body ourselves.
         * This is why we have to parse the URL for parameters ourselves.
         */
        Properties parameters = ParsingUtilities.parseUrlParameters(request);
        String subCommand = parameters.getProperty("subCommand");
        if ("load-dataset".equals(subCommand)) {
            doLoadDataset(request, response, parameters);
        } else {
            HttpUtilities.respond(response, "error", "No such sub command");
        }
    }

    private void doGetAvailableCharts(HttpServletRequest request, HttpServletResponse response, Properties parameters)
        throws ServletException, IOException {

        HttpUtilities.respond(response, "error", "Not implemented yet");
        return;
    }
    
    private void doGetSelectedChart(HttpServletRequest request, HttpServletResponse response, Properties parameters)
        throws ServletException, IOException {

        HttpUtilities.respond(response, "error", "Not implemented yet");
        return;
    }
    
    private void doLoadDataset(HttpServletRequest request, HttpServletResponse response, Properties parameters)
        throws ServletException, IOException {
        
        Project project = getProject(request);
        VisualizeManager pvm;
        
        if (project == null  || (pvm = project.getVisualizeManager()) == null) {
            HttpUtilities.respond(response, "error", "No such project");
            return;
        }
            
        String row0 = parameters.getProperty("row0");
        String row1 = parameters.getProperty("row1");
        String col0 = parameters.getProperty("col0");
        String col1 = parameters.getProperty("col1");
        String measures = parameters.getProperty("measures");
        
        JSONObject ds;
        try {
            ds = pvm.buildDataset(row0, row1, col0, col1, measures);
            respond(response, ds.toString());
        } catch (JSONException e) {
            respond(response, "");
        }
    }
}
