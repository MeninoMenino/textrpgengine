package processor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Scene;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class SceneProcessor {

    Gson gson = new Gson();

    //Process all game scenes
    public Map<Integer, Scene> processScenes() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/scenes.json"))));
            List<Scene> sceneList = gson.fromJson(reader, new TypeToken<List<Scene>>(){}.getType());

            return sceneList.stream()
                    .collect(Collectors.toMap(Scene::getId, scene -> scene));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

}
