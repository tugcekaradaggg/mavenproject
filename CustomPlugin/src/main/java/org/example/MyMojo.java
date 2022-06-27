package org.example;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Developer;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


@Mojo(name="summarize", defaultPhase = LifecyclePhase.INITIALIZE)
public class MyMojo extends AbstractMojo  {
    /**
     * Location of the file.
     * @parameter expression="${project.build.directory}"
     * @required
     */
    private File outputDirectory;

    @Parameter(property = "file.path", defaultValue = "out.txt")
    private String fileName;

    @Parameter(property = "project", readonly = true)
    private MavenProject project;

    public void execute() throws MojoExecutionException  {

        String path = fileName;
        String target = "target";
        File f = new File(target);
        File touch = new File(f, path);

        String version = project.getVersion();
        String groupId = project.getGroupId();
        String artficatId = project.getArtifactId();
        List<Dependency> dependencies = project.getDependencies();
        List<Plugin> plugins = project.getBuildPlugins();
        List<Developer> developers = project.getDevelopers();
        String releaseDate = project.getProperties().getProperty("release-date");

        try {
            FileWriter w = new FileWriter(touch);
            w.write("Project info : " + groupId + "." + artficatId + "." + version+ "\n");
            w.write("Developers :");
            for(int i = 0; i<developers.size(); i++){
                w.write("Developer " + i+1 + " Name : " + developers.get(i).getName() + "\n");
            }
            w.write("Release Date : " + releaseDate + "\n");
            w.write("Dependecies \n");
            for(int i = 0; i<dependencies.size(); i++){
                w.write("Dependency : " + dependencies.get(i).getGroupId() + "." + dependencies.get(i).getArtifactId() + "\n");
            }
            w.write("Plugins\n");
            for(int i = 0; i<plugins.size(); i++){
                w.write("Plugin : " + plugins.get(i).getArtifactId());
            }

            w.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        /*
        File f = outputDirectory;

        if ( !f.exists() ) {
            f.mkdirs();
        }

        File touch = new File( f, "touch.txt" );

        FileWriter w = null;
        try {
            w = new FileWriter( touch );

            w.write( "touch.txt" );
        }
        catch ( IOException e )
        {
            throw new MojoExecutionException( "Error creating file " + touch, e );
        }
        finally
        {
            if ( w != null )
            {
                try
                {
                    w.close();
                }
                catch ( IOException e )
                {
                    // ignore
                }
            }
        }
        */
    }


}
