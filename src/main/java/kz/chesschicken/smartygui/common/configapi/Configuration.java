/**
 * Copyright 2022 ChessChicken-KZ
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kz.chesschicken.smartygui.common.configapi;

import kz.chesschicken.smartygui.common.configapi.instance.Group;
import kz.chesschicken.smartygui.common.configapi.instance.Property;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Configuration {
    private static final File configFolder = new File(FabricLoader.getInstance().getGameDirectory(),"config");

    private static List<String> parseConfig(File s) {
        List<String> items = new ArrayList<>();

        try (Stream<String> stream = Files.lines(s.toPath())) {
            items = stream.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return items;
    }

    private static Object parseLine(String s) {
        try
        {
            if(s.startsWith("(") && s.endsWith(")")) {
                return s.replace("(", "").replace(")", "").split(",");
            } else if (s.startsWith("\"") && s.endsWith("\"")) {
                return s.replace("\"", "");
            } else if (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false")) {
                return Boolean.parseBoolean(s);
            } else if (s.endsWith("D") || s.endsWith("d")) {
                return Double.parseDouble(s);
            } else if (s.endsWith("F") || s.endsWith("f")) {
                return Float.parseFloat(s);
            } else {
                return Integer.parseInt(s);
            }
        } catch (NumberFormatException e)
        {
            return s;
        }
    }

    private static String formatString(Property property) {
        switch (property.getType()) {
            case BOOLEAN:
                return String.valueOf((boolean) property.getValue());
            case INTEGER:
                return String.valueOf((int) property.getValue());
            case DOUBLE:
                return String.valueOf((double) property.getValue()) + "D";
            case FLOAT:
                return String.valueOf((float) property.getValue()) + "F";
            case STRING_ARRAY:
                return "(" + String.join(",", (String[]) property.getValue()).trim() + ")";


            default:
                return String.valueOf(property.getValue());
        }
    }

    private final File fileLoc;
    private final List<Group> groupList;
    private boolean allowThiccness = false;

    public Configuration(String s) {
        this.fileLoc = getConfigFile(s);
        this.groupList = new ArrayList<>();
    }

    private File getConfigFile(String name) {
        File f = new File(configFolder, name);
        if(!f.exists()) f.mkdir();
        return new File(f, name + ".config");
    }

    public void add(Group g) {
        groupList.add(g);
    }

    public boolean exists() {
        return fileLoc.exists();
    }
    
    public void destroyFile() {
    	if(exists()) fileLoc.delete();
    }

    public void addThickness(boolean b) {
        this.allowThiccness = b;
    }

    public Group getGroup(String groupName) {
        for(Group g : groupList) {
            if(g.getName().equalsIgnoreCase(groupName))
                return g;
        }
        return null;
    }

    public void cleanup() {
        this.groupList.clear();
    }

    public void load() {
        List<String> conf = parseConfig(fileLoc);

        Group temp1 = null;

        for(String prop: conf) {
            if(prop.trim().startsWith("#") || prop.trim().startsWith("{") || prop.trim().startsWith("}"))
                continue;

            if(prop.trim().startsWith("group")) {
                Group g = Group.createGroup(prop.trim().split(" ")[0].replaceAll("group:", ""));
                groupList.add(g);
                temp1 = g;
                continue;
            }

            if(prop.trim().length() > 0) {
                String[] args = prop.trim().split("=");
                if(temp1 != null) {
                    Property property = Property.createProperty(args[0], parseLine(args[1]));
                    temp1.add(property);
                }
            }
        }
    }

    public void save() {
        try {
            if(!fileLoc.exists()) {
                fileLoc.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(fileLoc);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            for(Group g : groupList) {
                printWriter.println("");
                if(g.isCommentaryPresent()) {
                    String[] lists = g.getCommentary().split("\n");
                    for(String s : lists)
                    	printWriter.println("#" + s);
                }
                printWriter.println("group:"+g.getName());

                for(int c = 0; c < g.getProperties().size(); c++) {
                    Property p = g.getProperties().get(c);
                    if(p.isCommentaryPresent()) {
                        String[] lists = p.getCommentary().split("\n");
                        for(String s : lists)
                        	printWriter.println("\t#" + s);
                    }
                    printWriter.println("\t" + p.getName() + "=" + formatString(p));
                    if(allowThiccness && c + 1 != g.getProperties().size())
                        printWriter.println("");
                }
            }
            printWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
