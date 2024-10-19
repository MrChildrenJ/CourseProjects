let asteroidNum = 5;

function setAsteroids(number) {
  asteroidNum = number;
  document.querySelector("#asteroid-dialog").style.display = "none";
  gameStart();
}

function setCustomAsteroids() {
  let customNumber = document.querySelector("#custom-number").value;
  if (customNumber && customNumber > 0 && customNumber <= 50) {
    setAsteroids(parseInt(customNumber));
  } else {
    alert("Please enter a valid number between 1 and 50.");
  }
}

window.onload = function () {
  document.querySelector("#asteroid-dialog").style.display = "block";
};

function gameStart() {
  document.querySelector("#mycanvas").style.display = "block";

  let startTime = Date.now();
  let gameOver = false;
  let survivalTime = 0;

  function isCollision(obj1, obj2) {
    let dx = obj1.x - obj2.x;
    let dy = obj1.y - obj2.y;
    let distance = Math.sqrt(dx * dx + dy * dy);
    return distance < (obj1.width + obj2.width) / 4;
  }

  function handleCollision() {
    for (let a of asteroids) {
      if (isCollision(ship, a)) {
        gameOver = true;
        survivalTime = Date.now() - startTime;
        return;
      }
    }
  }

  let can = document.querySelector("#mycanvas");
  let context = can.getContext("2d");
  can.width = window.innerWidth;
  can.height = window.innerHeight;

  let ship = {
    x: can.width / 2,
    y: can.height / 2,
    speed: 5 + Math.random() * 5,
  };

  let asteroids = [];
  for (let i = 0; i < asteroidNum; i++) {
    asteroids.push({
      x: Math.random() * can.width,
      y: Math.random() * can.height,
      speed: 3 + Math.random() * 3,
    });
  }

  function loadImage(src) {
    return new Promise((resolve, reject) => {
      const img = new Image();
      img.onload = () => resolve(img);
      img.onerror = reject;
      img.src = src;
    });
  }

  Promise.all([loadImage("ship.png"), loadImage("asteroid.png")])
    .then(([shipImg, asteroidImg]) => {
      // loadImage('ship.png) -> shipImg
      // loadImage('asteroid.png') -> asteroidImg
      ship.img = shipImg;
      ship.width = shipImg.width;
      ship.height = shipImg.height;

      asteroids.forEach((asteroid) => {
        asteroid.img = asteroidImg;
        asteroid.width = asteroidImg.width;
        asteroid.height = asteroidImg.height;
      });
      mainGameLoop();
    })
    .catch((error) => {
      console.error("Failed to load images: ", error);
    });

  let cursorLocation = { x: 0, y: 0 };

  function handleMouseMove(event) {
    cursorLocation.x = event.x;
    cursorLocation.y = event.y;
  }

  can.addEventListener("mousemove", handleMouseMove);

  function handleMove(obj, target, speed) {
    let dx = target.x - obj.x;
    let dy = target.y - obj.y;
    let distance = Math.sqrt(dx * dx + dy * dy);

    if (distance > speed) {
      obj.x += (dx / distance) * speed;
      obj.y += (dy / distance) * speed;
    } else {
      obj.x = target.x;
      obj.y = target.y;
    }
  }

  function handleShipMove() {
    handleMove(ship, cursorLocation, ship.speed);
  }

  function handleAsteroidMove() {
    asteroids.forEach((asteroid) => {
      handleMove(asteroid, ship, asteroid.speed);
    });
  }

  function draw() {
    context.fillStyle = "black";
    context.fillRect(0, 0, can.width, can.height);

    // context.clearRect(0, 0, can.width, can.height);
    context.drawImage(
      ship.img,
      ship.x - ship.img.width / 2,
      ship.y - ship.img.height / 2
    );
    asteroids.forEach((asteroid) => {
      context.drawImage(
        asteroid.img,
        asteroid.x - asteroid.img.width / 2,
        asteroid.y - asteroid.img.height / 2
      );
    });

    context.fillStyle = "whitesmoke";
    context.font = "20px Courier";
    let time = (survivalTime / 1000).toFixed(1);
    context.fillText(`Survival Time: ${time} secs`, 10, 30);
  }

  function mainGameLoop() {
    if (!gameOver) {
      handleShipMove();
      handleAsteroidMove();
      handleCollision();
      survivalTime = Date.now() - startTime;
      draw();
      requestAnimationFrame(mainGameLoop);
    } else {
      alert(
        "Game Over\n\nYou survived for " +
          (survivalTime / 1000).toFixed(1) +
          " secs"
      );
    }
  }

  window.addEventListener("resize", function () {
    can.width = window.innerWidth;
    can.height = window.innerHeight;
  });
}
