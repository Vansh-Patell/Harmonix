/****************************
 * Vision statement slideshow
 ****************************/
let currentSlide = 0;

const slides = [
  {
    title: "Audience",
    text: "Harmonix is an innovative music management application that caters to any music enthusiasts that have different musical preferences and enjoy listening to music at any time and any place. Harmonix aims to help users engage with, and enjoy music to their own individual preference anyway they desire and be able to manage catalogs of created playlists efficiently, that supports organization based on albums,artists and more. Harmonix allows users to get recommendations based on their preferences and share their favorite songs and playlists with others, with an easy to navigate layout that will be accessible by anybody.",
  },
  {
    title: "Functionality",
    text: "By allowing users to create personal accounts and libraries, Harmonix offers a tailored music experience. This personalization enhances user satisfaction and engagement. The app's feature to discover new music based on user preferences is particularly valuable for users looking to expand their musical horizons and find new artists or genres that align with their tastes. By integrating social media, Harmonix will also enhance the social experience of music listening. The ability to download music for offline listening is a significant benefit for users who might have limited internet access or wish to conserve data usage. Features for sorting and selecting music in the library, along with playlist creation and management reduce the time and effort required to find or organize music.",
  },
  {
    title: "Success Criteria",
    text: "The project will be considered a success based off a few criteria; Based off surveys & interviews between usage of common music library apps and our app, we will determine which app users prefer, with ours being chosen the most being the goal. Another part of our success criteria is that the minimum number of steps to accomplish any common action between the most popular music management apps and our app should be minimal or at the most should take 5 steps to accomplish, with the reasoning behind the goal being to make our app as simple as possible for users.",
  },
  {
    title: "Overview",
    text: "The Harmonix is a go-to music management app, that will be used to make a user's music experience effortless and enjoyable. It's a place to explore new genres, craft personalized playlists, and have your entire music library at your fingertips. Unlike other music apps, this app is created to be user-friendly and straightforward, ensuring that even individuals with limited tech expertise can easily navigate through it.",
  },
];

// Attach event listener to right arrow
document.querySelector(".arrow-next").addEventListener("click", nextSlide);

function nextSlide() {
  // Get the new slide data
  const newSlide = slides[currentSlide++];
  // Update the h1 and p content
  document.querySelector("#overview h1").textContent = newSlide.title;
  document.querySelector("#overview p").textContent = newSlide.text;
  if (currentSlide == slides.length) {
    currentSlide = 0;
  }
}

//-----------------------------------------------------------------------------------------------------
/****************************
 * Contributors slideshow
 * Changes every 8 seconds
 ****************************/
let currentDevSlide = 0;
const devSlides = [
  {
    title: "Leon Bauer",
    text: "I have gained multiple skills throughout the term, in terms of development. I learned how to use version control to collaborate with group members and to keep a project organized. I learned how and when to apply design patters to solve and simplify different issues and cases within the project, such as using a singleton to limit access to the music player to a single instance. I also learned how to test between the three layers of the 3-tier architecture of our project, via applying unit tests, integration tests, and system tests to guarantee proper functionality of features.",
  },
  {
    title: "Linpu Zhang",
    text: "I learned how to write tests before coding, ensuring the quality of my code. I also improved my collaboration skills by effectively managing code changes using Git for version control. Practicing Agile methodologies helped me prioritize tasks and adapt to changing requirements. Lastly, troubleshooting issues during development  & before releases help me sharpen my problem-solving abilities and debugging skills.",
  },
  {
    title: "Filip Karamanov",
    text: "A special development skill that I gained throughout the project is the ability to test my project thoroughly with the different types of tests that we learned and used throughout the semester. By writing unit tests, integration tests, and system tests, I have gained the ability, and understanding of the importance of testing code thoroughly at various levels, which I know will be useful for me in the future.",
  },
  {
    title: "Krish Mangat",
    text: "I learnt the skill to manage my time and communicate with the team in times when I could/could not do a task. I also learnt how to combine layers as it always seemed like magic. I only knew about unit tests, so learning new ways to test code was fascinating.",
  },
  {
    title: "Vansh Patel",
    text: "Throughout this project, along with understanding of technical aspects like software design patterns and testing, I also gained valuable skills such as project management, collaborating with team members who brought diverse opinions and ideas and quickly adapting to changes.",
  },
];

function nextDevSlide() {
  // Get the new slide data
  const newSlide = devSlides[currentDevSlide];

  // Update the h1 and p content
  document.querySelector(".dev-slide h1").textContent = newSlide.title;
  document.querySelector(".dev-slide p").textContent = newSlide.text;

  // Increment currentDevSlide
  currentDevSlide++;

  if (currentDevSlide === devSlides.length) {
    currentDevSlide = 0;
  }
}

// Change slides every 8 seconds
setInterval(nextDevSlide, 8000);

//-----------------------------------------------------------------------------------------------------
