/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.myfaces.html5.examples.showcase.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;

@ManagedBean(name="registerBean")
public class RegisterBean
{

    private String name;
    private String username;
    private String password;
    private String webSite;
    private String comment;
    private String favoriteMovieName;
    private String eyeColor;

    public List<SelectItem> getMovieSuggestionItems()
    {
        List<SelectItem> items = new ArrayList<SelectItem>();

        if (favoriteMovieName != null)
        {
            int i = 0;
            for (String movie : movieNames)
            {
                if (movie.startsWith(this.favoriteMovieName)){
                    items.add(new SelectItem(movie, null));
                    if(i++ > 20)
                        break;
                }
            }
        }

        return items;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getWebSite()
    {
        return webSite;
    }

    public void setWebSite(String webSite)
    {
        this.webSite = webSite;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getFavoriteMovieName()
    {
        return favoriteMovieName;
    }

    public void setFavoriteMovieName(String favoriteMovieName)
    {
        this.favoriteMovieName = favoriteMovieName;
    }

    public String getEyeColor()
    {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor)
    {
        this.eyeColor = eyeColor;
    }

    private static String[] movieNames = new String[]
    {
            "Java 4 Ever", "The Shawshank Redemption", "The Godfather", "The Godfather: Part II",
            "The Good, the Bad and the Ugly", "Pulp Fiction", "Toy Story 3", "Schindler's List", "12 Angry Men",
            "One Flew Over the Cuckoo's Nest", "Star Wars: Episode V - The Empire Strikes Back", "The Dark Knight",
            "The Lord of the Rings: The Return of the King", "Star Wars: Episode IV - A New Hope", "Seven Samurai",
            "Casablanca", "Goodfellas", "Fight Club", "City of God",
            "The Lord of the Rings: The Fellowship of the Ring", "Raiders of the Lost Ark", "Rear Window", "Psycho",
            "The Usual Suspects", "Once Upon a Time in the West", "The Silence of the Lambs", "The Matrix", "Se7en",
            "Memento", "It's a Wonderful Life", "The Lord of the Rings: The Two Towers", "Sunset Blvd.",
            "Dr. Strangelove or: How I Learned to Stop Worrying and Love the Bomb", "North by Northwest",
            "Citizen Kane", "The Professional", "Apocalypse Now", "Forrest Gump", "American Beauty",
            "American History X", "Taxi Driver", "Terminator 2: Judgment Day", "Vertigo", "Lawrence of Arabia",
            "Alien", "Amélie", "Saving Private Ryan", "WALL·E", "The Shining", "A Clockwork Orange", "Paths of Glory",
            "The Departed", "The Pianist", "To Kill a Mockingbird", "Aliens", "The Lives of Others", "Spirited Away",
            "M", "Double Indemnity", "Eternal Sunshine of the Spotless Mind", "Chinatown", "Requiem for a Dream",
            "L.A. Confidential", "Reservoir Dogs", "The Third Man", "Das Boot", "The Treasure of the Sierra Madre",
            "Monty Python and the Holy Grail", "City Lights", "Pan's Labyrinth", "The Bridge on the River Kwai",
            "Raging Bull", "The Prestige", "Back to the Future", "Inglourious Basterds", "2001: A Space Odyssey",
            "Life Is Beautiful", "Modern Times", "Singin' in the Rain", "Some Like It Hot", "Amadeus", "Up",
            "Downfall", "Full Metal Jacket", "Braveheart", "Cinema Paradiso", "The Maltese Falcon",
            "Once Upon a Time in America", "All About Eve", "Rashômon", "Metropolis", "The Green Mile", "Gran Torino",
            "The Elephant Man", "The Great Dictator", "Sin City", "Rebecca", "The Apartment", "Gladiator", "The Sting",
            "The Great Escape", "Indiana Jones and the Last Crusade", "Slumdog Millionaire", "Avatar",
            "Star Wars: Episode VI - Return of the Jedi", "Unforgiven", "Bicycle Thieves", "Jaws", "Batman Begins",
            "Die Hard", "Blade Runner", "On the Waterfront", "Oldboy", "Mr. Smith Goes to Washington", "Hotel Rwanda",
            "No Country for Old Men", "Touch of Evil", "The Seventh Seal", "Fargo", "Princess Mononoke",
            "For a Few Dollars More", "The Wizard of Oz", "District 9", "Heat", "Strangers on a Train",
            "Cool Hand Luke", "Donnie Darko", "High Noon", "The Sixth Sense", "Notorious", "The Deer Hunter",
            "There Will Be Blood", "Snatch.", "Annie Hall", "Kill Bill: Vol. 1", "The Manchurian Candidate",
            "The General", "The Big Lebowski", "Platoon", "Yojimbo", "Into the Wild", "Ran", "Ben-Hur", "The Wrestler",
            "The Big Sleep", "Million Dollar Baby", "The Lion King", "It Happened One Night",
            "Witness for the Prosecution", "Life of Brian", "Butch Cassidy and the Sundance Kid", "Toy Story",
            "The Bourne Ultimatum", "Wild Strawberries", "Finding Nemo", "Trainspotting", "Gone with the Wind",
            "Kick-Ass", "Stand by Me", "The Terminator", "Groundhog Day", "The Graduate", "Scarface", "The Thing",
            "Amores Perros", "Star Trek", "Dog Day Afternoon", "Ratatouille", "Gandhi", "V for Vendetta",
            "Lock, Stock and Two Smoking Barrels", "The Wages of Fear", "How to Train Your Dragon", "Twelve Monkeys",
            "The Grapes of Wrath", "The Secret in Their Eyes", "The Gold Rush", "Casino", "8½",
            "Grave of the Fireflies", "Diabolique", "The Night of the Hunter", "Judgment at Nuremberg",
            "The Incredibles", "The Princess Bride", "The Killing", "The Wild Bunch", "Kind Hearts and Coronets",
            "Children of Men", "The Exorcist", "In Bruges", "Sunrise: A Song of Two Humans",
            "The Best Years of Our Lives", "The Kid", "Nights of Cabiria", "The Hustler", "Dial M for Murder",
            "Good Will Hunting", "Rosemary's Baby", "Ed Wood", "Harvey", "Big Fish", "King Kong",
            "A Streetcar Named Desire", "Let the Right One In", "The Diving Bell and the Butterfly", "Sleuth",
            "Magnolia", "Kill Bill: Vol. 2", "Rocky", "Letters from Iwo Jima", "Shadow of a Doubt", "Mystic River",
            "Stalag 17", "Network", "Brief Encounter", "The African Queen", "Rope", "Crash (200",
            "Pirates of the Caribbean: The Curse of the Black Pearl", "Bonnie and Clyde", "The Battle of Algiers",
            "Duck Soup", "Planet of the Apes", "Manhattan", "La strada", "Patton", "The 400 Blows", "The Conversation",
            "Crouching Tiger, Hidden Dragon", "Changeling", "The Curious Case of Benjamin Button", "Barry Lyndon",
            "Little Miss Sunshine", "All Quiet on the Western Front", "The Truman Show",
            "The Nightmare Before Christmas", "Anatomy of a Murder", "Toy Story 2", "The Adventures of Robin Hood",
            "Mulholland Dr.", "Who's Afraid of Virginia Woolf?", "Spartacus", "Monsters, Inc.", "Shaun of the Dead",
            "Ikiru", "My Neighbor Totoro", "The Philadelphia Story", "Glory", "Rain Man", "Arsenic and Old Lace",
    };
}
