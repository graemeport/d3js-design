/*

Copyright 2011, Google Inc.
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

package com.google.refine.visualize;

import javax.servlet.ServletException;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.refine.model.Project;

import com.google.refine.RefineServlet;

public class VisualizeManager {
    
    final static Logger logger = LoggerFactory.getLogger("visualise");
    
    static private RefineServlet servlet;
    
    private Project project; 
    
    static public void initialize(RefineServlet servlet) {
        VisualizeManager.servlet = servlet;
    }

    public VisualizeManager(Project proj) {
        project = proj;
    }
    
    public JSONObject buildDataset(String row0, String row1, String col0, String col1, String measures)
        throws JSONException {
        
        String data =
"life : {" +
"    rows : [" +
"        r0 : {" +
"            desc : [" +
"                { name : \"Region\", type : \"text\" }" +
"            ]," +
"            values : [" +
"                { id : \"EAS\", Region : \"East Asia & Pacific\" }," +
"                { id : \"ECS\", Region : \"Europe & Central Asia\" }," +
"                { id : \"LCN\", Region : \"Latin America & Caribbean\" }," +
"                { id : \"MEA\", Region : \"Middle East & North Africa\" }," +
"                { id : \"NAC\", Region : \"North America\" }," +
"                { id : \"SAS\", Region : \"South Asia\" }," +
"                { id : \"SSF\", Region : \"Sub-Saharan Africa\" }" +
"            ]" +
"        }," +
"        r1 : {" +
"            desc : [" +
"                { name : \"Country\", type : \"text\" }" +
"            ]," +
"            values : [" +
"                { id : \"AFG\", pid : \"SAS\", Country : \"Afghanistan\" }," +
"                { id : \"ALB\", pid : \"ECS\", Country : \"Albania\" }," +
"                { id : \"DZA\", pid : \"MEA\", Country : \"Algeria\" }," +
"                { id : \"AGO\", pid : \"SSF\", Country : \"Angola\" }," +
"                { id : \"ARG\", pid : \"LCN\", Country : \"Argentina\" }," +
"                { id : \"ARM\", pid : \"ECS\", Country : \"Armenia\" }," +
"                { id : \"ABW\", pid : \"LCN\", Country : \"Aruba\" }," +
"                { id : \"AUS\", pid : \"EAS\", Country : \"Australia\" }," +
"                { id : \"AUT\", pid : \"ECS\", Country : \"Austria\" }," +
"                { id : \"AZE\", pid : \"ECS\", Country : \"Azerbaijan\" }" +
"            ]" +
"        }" +
"    ]" +
"    columns : [" +
"        c0 : {" +
"            desc : [" +
"                { name : \"Year\", type : \"numeric\" }" +
"            ]," +
"            values : [" +
"                { Year : 1960 }," +
"                { Year : 1961 }," +
"                { Year : 1962 }," +
"                { Year : 1963 }," +
"                { Year : 1964 }," +
"                { Year : 1965 }," +
"                { Year : 1966 }," +
"                { Year : 1967 }," +
"                { Year : 1968 }," +
"                { Year : 1969 }" +
"            ]" +
"    }," +
"    measures : {" +
"        desc : [" +
"            { name : \"Life Expectancy\", type : \"numeric\" }" +
"        ]" +
"    data : [" +
"        [ 31.13209756,31.47529268,31.83202439,32.20426829,32.59104878,32.98987805,33.39673171,33.80914634,34.22307317,34.6375122 ]," +
"        [ 62.25436585,63.27346341,64.16234146,64.88709756,65.43768293,65.82943902,66.10007317,66.31180488,66.51643902,66.73846341 ]," +
"        [ 46.98539024,47.51836585,48.06885366,48.63982927,49.23080488,49.83829268,50.45434146,51.07292683,51.68958537,52.30278049 ]," +
"        [ 32.98534146,33.38621951,33.78709756,34.18795122,34.58831707,34.98917073,35.3915122,35.79585366,36.20119512,36.60604878 ]," +
"        [ 65.17514634,65.29663415,65.3892439,65.46553659,65.53907317,65.62587805,65.74095122,65.8882439,66.07221951,66.29331707 ]," +
"        [ 65.85395122,66.27546341,66.7014878,67.13002439,67.55809756,67.98473171,68.40939024,68.82663415,69.22641463,69.59870732 ]," +
"        [ 65.56936585,65.98802439,66.36553659,66.71397561,67.04429268,67.3697561,67.699,68.03468293,68.37714634,68.72841463 ]," +
"        [ 70.81707317,70.97317073,70.94243902,70.91170732,70.88097561,70.8502439,70.8195122,70.86926829,70.91902439,70.96878049 ]," +
"        [ 68.58560976,69.57731707,69.3095122,69.44365854,69.92195122,69.72219512,70.04585366,69.91780488,70.05756098,69.83317073 ]," +
"        [ 60.8362439,61.23917073,61.64458537,62.052,62.45741463,62.86182927,63.26726829,63.66873171,64.05670732,64.41868293 ]" +
"    ]" +
"}";
        JSONObject result = null;
        try {
            result = new JSONObject(data);
        } catch (JSONException e) {
            logger.error("JSON error in buildDataset", e);
        }
        
        return result;
    }
}
