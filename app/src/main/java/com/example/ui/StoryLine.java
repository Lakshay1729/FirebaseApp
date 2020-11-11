package com.example.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.example.ui.viewmodels.StoryLineViewModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StoryLine extends AppCompatActivity {

    AppCompatImageView storyimage;
    AppCompatTextView storyname;
    AppCompatTextView blog;
    ArrayList characterImages = new ArrayList<>(Arrays.asList(R.drawable.ironman, R.drawable.hulk, R.drawable.strange, R.drawable.wolverine, R.drawable.daredevil, R.drawable.spiderman, R.drawable.thor));
    ArrayList characterNames = new ArrayList<>(Arrays.asList("IronMan", "Hulk", "Doctor Strange", "Wolverine", "Daredevil", "Spiderman", "Thor"));
    String blogs[] = new String[]{"Iron Man is a fictional superhero appearing in American comic books published by Marvel Comics. The character was co-created by writer and editor Stan Lee, developed by scripter Larry Lieber, and designed by artists Don Heck and Jack Kirby. The character made his first appearance in Tales of Suspense #39 (cover dated March 1963), and received his own title in Iron Man #1 (May 1968).\n" +
            "\n" +
            "A wealthy American business magnate, playboy, and ingenious scientist, Anthony Edward \"Tony\" Stark suffers a severe chest injury during a kidnapping. When his captors attempt to force him to build a weapon of mass destruction, he instead creates a mechanized suit of armor to save his life and escape captivity. Later, Stark develops his suit, adding weapons and other technological devices he designed through his company, Stark Industries. He uses the suit and successive versions to protect the world as Iron Man. Although at first concealing his true identity, Stark eventually publicly reveals himself to be Iron Man.></string>\n",
            "The Hulk is a fictional superhero appearing in publications by the American publisher Marvel Comics. Created by writer Stan Lee and artist Jack Kirby, the character first appeared in the debut issue of The Incredible Hulk (May 1962). In his comic book appearances, the character is both the Hulk, a green-skinned, hulking and muscular humanoid possessing a vast degree of physical strength, and his alter ego Dr. Robert Bruce Banner, a physically weak, socially withdrawn, and emotionally reserved physicist, the two existing as independent personalities and resenting of the other.Lee stated that the Hulk\\'s creation was inspired by a combination of Frankenstein and Dr. Jekyll and Mr. Hyde.[3] Although the Hulk's coloration has varied throughout the character's publication history, the most usual color is green.—>\n" +
                    "       ",
            " The character begins as an egotistical surgeon who loses the ability to operate after a car crash severely damages his hands. Searching the globe for healing, he encounters the Ancient One, the Sorcerer Supreme. Strange becomes his student, and learns to be a master of both the mystical and the martial arts. He acquires an assortment of mystical objects, including the powerful Eye of Agamotto and Cloak of Levitation, and takes up residence in a New York City mansion called the Sanctum Sanctorum. Strange assumes the title of Sorcerer Supreme and, with his friend and valet Wong, defends the world from mystical threats.—>-->\n" +
                    "<!--           Stephen Strange, M.D., Ph.D., is an egotistical doctor who only cares about wealth from his career. The bones in his hands are shattered in a car crash, leading to extensive nerve damage. His hands tremble uncontrollably rendering him unable to perform surgery. Too vain to accept a teaching job, Strange desperately searches for a way to restore the motor function in his hands.—>-->\n",

            " Wolverine (birth name; James Howlett;[1] alias; Logan and Weapon X) is a fictional character appearing in American comic books published by Marvel Comics, mostly in association with the X-Men. He is a mutant who possesses animal-keen senses, enhanced physical capabilities, a powerful regenerative ability known as a healing factor, and three retractable claws in each hand. Wolverine has been depicted variously as a member of the X-Men, Alpha Flight, and the Avengers.—>\n" +
                    "            Wolverine is typical of the many tough antiheroes that emerged in American popular culture after the Vietnam War;[4]:265 his willingness to use deadly force and his brooding nature became standard characteristics for comic book antiheroes by the end of the 1980s.[4]:277 As a result, the character became a fan favorite of the increasingly popular X-Men franchise,[4]:263, 265 and has been featured in his own solo comic book series since 1988.—>\n" +
                    "       ",

            "Daredevil's origins stem from a childhood accident that gave him special abilities. While growing up in the historically gritty or crime-ridden working class Irish-American neighborhood of Hell's Kitchen in New York City, Matt Murdock is blinded by a radioactive substance that falls from an out-of-control truck after he pushes a man out of the path of the oncoming vehicle. While he can no longer see, his exposure to the radioactive material heightens his remaining senses beyond normal human ability, and gives him a \"radar sense.\" His father, a boxer named Jack Murdock, is a single man raising his now blind son, who despite his rough upbringing, unconditionally loves his son and tries to teach him to form a better life for himself. Jack is later killed by gangsters after refusing to throw a fight, leaving Matt an orphan. In order to protect himself, Matt began training to hone his physical abilities and superhuman senses under the tutelage of a mysterious blind stranger named Stick, eventually becoming a highly-skilled and expert martial artist. Some years later, after graduating from law school with high grades, Matt seeks out the criminal element in Hell's Kitchen and starts his crime-fighting activities. Matt targets the local gangsters who murdered his father and succeeds in bringing them to justice. Eventually, donning a costumed attire modeled after a devil, Matt took up a dual life of fighting against the criminal underworld in New York City as the masked vigilante Daredevil, which put him in conflict with many super-villains, including his arch-enemies Bullseye and the Kingpin.[9] He also becomes a skilled and respected lawyer after graduating from Columbia Law School with his best friend and roommate, Franklin \"Foggy\" Nelson, with whom he becomes law partners, forming the law firm Nelson and Murdock.-->\n" +
                    "      ",
            "  A bite from a spider somehow granted teenager Peter Parker its arachnid abilities and instead of using them for personal gain, he decided to help others with them. An orphan living with his aunt, May Parker, the boy chose to wear a mask while fighting crime so as not to burden her with his actions.-->\n" +
                    "\n" +
                    "Calling himself Spider-Man and sporting a pair of web-shooting devices he’d constructed, Parker wound up in internet videos which attracted the attention of Tony Stark. The billionaire industrialist deduced Spider-Man’s secret identity and approached Parker at his and May’s home in Queens, New York with a request for aid from the hero in an upcoming confrontation with Captain America and a group of other rogue Avengers and associates. Parker was initially hesitant to even admit his secret career as Spider-Man, but the thrill of adventure and Stark’s talk of responsibility drew him in and he accepted the invitation, as well as a new, high-tech costume and web-shooters.-->\n" +
                    "            Peter Parker’s gifts from the spider bite include enhanced strength, stamina, and agility, as well as the ability to cling to nearly any surface by his hands and feet, and an internal “alarm” of sorts that warns him of impending danger. When in costume he becomes a figure in motion, leaping and jumping about with a steady stream of wisecracks.-->\n" +
                    "     ",
            " Thor Odinson is a fictional superhero appearing in American comic books published by Marvel Comics. The character, which is based on the Norse deity of the same name, is the Asgardian god of thunder who possesses the enchanted hammer, Mjolnir, which grants him the ability to fly and manipulate weather amongst his other superhuman attributes.-->\n" +
                    "<!--            Like all Asgardians, Thor is incredibly long-lived and relies upon periodic consumption of the Golden Apples of Idunn to sustain his extended lifespan, which to date has lasted many millennia. Being the son of Odin and the elder goddess Gaea, Thor is physically the strongest of the Asgardians.[75][96][214][215][216] Thor is capable of incredible feats of strength, such as lifting the almost Earth-sized Midgard Serpent,[217] supporting a weight equivalent to that of 20 planets,[218] and by combining his power with that of Beta Ray Bill, destroying Surtur's solar system-sized dimensional portal.[219] If pressed in battle, Thor is capable of entering into a state known as the \"Warrior's Madness\" (\"berserkergang\" in Norwegian and Danish alike), which will temporarily increase his strength and stamina tenfold, although in this state he attacks friend and foe alike.[93][220][221]-->\n" +
                    "\n" +
                    "<!--Thor possesses a very high resistance to physical injury that approaches invulnerability.[222][223][224] He has even survived energy blasts from Celestials.[225] Thor possesses keen senses[58] that allow him to track objects traveling faster than light[226] and hear cries from the other side of the planet.[227] Thor has the ability to travel through time.[65] His stamina allowed him to battle the entire Frost Giant army for nine months without any sustenance or rest;[228] Thor has shown the ability to regenerate wounded portions of his body,[229] including entire limbs or organs, with the aid of magical forces such as Mjolnir.[229] Thor has superhuman speed, agility, and reflexes, enabling him to deflect bullets with his hammer,[230] and to swing or throw it at many times the speed of light.[231][232][233] In early stories, Thor has shown to be capable of vortex breath, which produces powerful winds.[65] Like all Asgardians, he has immunity to all Earthly diseases and some resistance to magic. Exceptionally powerful magic can overwhelm Odin's enchantment that transforms him between Asgardian and mortal forms.-->\n" +
                    "<!--    "
    };
    private Map<String, Object> doc;
    private HashMap<String, Object> character;
    private StoryLineViewModel storyLineViewModel;
    private SharedPreferences sharedPreference;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_story_line);
        storyimage = (AppCompatImageView) findViewById(R.id.story_image);
        storyname = (AppCompatTextView) findViewById(R.id.story_name);
        blog = (AppCompatTextView) findViewById(R.id.blog);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        sharedPreference=getSharedPreferences("Shared", Context.MODE_PRIVATE);
        id=sharedPreference.getString("pos","null");
        storyLineViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(StoryLineViewModel.class);
        storyLineViewModel.getData().observe(this, new Observer<DocumentSnapshot>() {
            @Override
            public void onChanged(DocumentSnapshot documentSnapshot) {
                Glide.with(getApplicationContext()).load(documentSnapshot.get("ImageLink")).into(storyimage);
                storyname.setText(documentSnapshot.get("CharacterName").toString());
                blog.setText(documentSnapshot.get("CharacterDescription").toString());
            }
        });
        storyLineViewModel.liked.observe(StoryLine.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                character = new HashMap<String, Object>();
                character.put("liked", storyLineViewModel.liked.getValue());
                storyLineViewModel.updateData(character);
            }
        });
        findViewById(R.id.like).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storyLineViewModel.setColor();
                storyLineViewModel.liked.setValue(!storyLineViewModel.liked.getValue());
            }
        });

    }
}
