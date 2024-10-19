#pragma once
#include "laser.h"
#include "window.h"
#include <SFML/Graphics.hpp>

struct KeyStatus {
  bool Up;
  bool Left;
  bool Right;
  bool Space;

  KeyStatus(bool initial)
      : Up(initial), Left(initial), Right(initial), Space(initial) {}
};

class Ship : public sf::Drawable, public sf::Transformable {
private:
  const uint32_t RATE_OF_FIRE = 400;
  const float TURN_SPEED = 0.033f;
  const float ACCELERATION = 150.f; // TODO tune this

  sf::Texture m_texture;
  sf::Sprite sprite;

  sf::Clock cooldown;

  KeyStatus KeyDown;

  float m_velocity;
  Vector2f m_direction;

  int hitCount = 0;
  bool isBlinking = false;
  Clock blinkClock;
  const float blinkDuration = 1.0f;

  virtual void draw(sf::RenderTarget &target,
                    sf::RenderStates states) const override {
    states.transform *= getTransform();
    target.draw(sprite, states);
  }

public:
  Ship(float x, float y);
  ~Ship() {}
  sf::Sprite &getSprite() { return sprite; }
  void handleInput(Event &e);
  void update(vector<Laser> &lasers, float &deltaTime);

  Vector2f getDirection() const { return m_direction; }
  Vector2f getNosePosition() const;

  bool checkCollision(const Sprite &asteroid);
  FloatRect getGlobalBounds() const;
};
