#include "game.h"
#include "window.h"

Game::Game()
    : window("Asteroids", Vector2u(WINDOW_WIDTH, WINDOW_HEIGHT)),
      player(window.GetWindowSize().x / 2.0f, window.GetWindowSize().y / 2.0f) {
  // Set up score font and text
  font.loadFromFile("IosevkaNerdFont-Bold.ttf");
  scoreText.setFont(font);
  scoreText.setCharacterSize(24);
  scoreText.setFillColor(sf::Color::White);
  score = 0;

  // initialize the asteroids
  vector<string> textureFiles = {"images/asteroid_04.png",
                                 "images/asteroid_05.png",
                                 "images/asteroid_06.png"};
  textures = loadTextures(textureFiles);

  Asteroid::initializeAsteroids(asteroids, textures, window.GetWindowSize(),
                                NUMBER_OF_INITIAL_ASTEROIDS);

  clock.restart();
}

void Game::run() {
  while (!window.IsDone()) {
    float deltaTime = clock.restart().asSeconds();
    Event event;

    // detection for closing window
    while (window.GetWindow().pollEvent(event)) {
      if (event.type == sf::Event::Closed ||
          event.type == sf::Keyboard::isKeyPressed(sf::Keyboard::Escape))
        window.Destroy();

      // detection for shooting laser
      player.handleInput(event);
    }

    player.update(lasers, deltaTime);

    for (const auto &asteroid : asteroids) {
      if (player.checkCollision(asteroid.getSprite())) {
        cout << "Collision detected!" << endl;
      }
    }

    asteroidSpawnTimer += deltaTime;
    if (asteroidSpawnTimer >= 3.0f) {
      Asteroid::initializeAsteroids(asteroids, textures, window.GetWindowSize(),
                                    1);
      asteroidSpawnTimer = 0.0f; // Reset the timer
    }

    // let asteroids move and rotate
    updateAsteroids(asteroids, deltaTime, window.GetWindowSize());

    // update lasers
    for (auto &laser : lasers)
      laser.update(deltaTime);

    // collision detection
    for (size_t i = 0; i < asteroids.size(); ++i)
      for (auto it = lasers.begin(); it != lasers.end();) {
        if (asteroids[i].isHitByLaser(it->getSprite())) {
          breakApart(asteroids, i, textures);
          score += 100;
          it = lasers.erase(it); // remove the laser after collision
        } else
          ++it;
      }

    lasers.erase(remove_if(lasers.begin(), lasers.end(),
                           [this](const Laser &laser) {
                             return laser.isOutsideWindow(
                                 window.GetWindowSize());
                           }),
                 lasers.end());

    window.BeginDraw();

    window.RenderAsteroids(asteroids);
    window.Draw(player.getSprite());
    for (auto &laser : lasers)
      window.Draw(laser.getSprite());

    scoreText.setString("SCORE " + to_string(score));
    window.Draw(scoreText);

    window.EndDraw();
  }
}
