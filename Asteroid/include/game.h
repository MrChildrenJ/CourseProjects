#ifndef game_hpp
#define game_hpp

#include "asteroid.h"
#include "ship.h"
#include "window.h"
#include <SFML/Graphics.hpp>
#include <iostream>
#include <memory>
#include <stdio.h>
#include <vector>
#define NUMBER_OF_INITIAL_ASTEROIDS 10

using namespace sf;
using namespace std;

class Game {
public:
  Game();
  void initializeGame();
  void run();

private:
  Font font;
  Text scoreText;
  int score;
  helper::Window window;
  Ship player;
  vector<unique_ptr<Texture>> textures;
  vector<Asteroid> asteroids;
  vector<Laser> lasers;
  Clock clock;
  float asteroidSpawnTimer = 0.0f;
};

#endif /* game_hpp */
