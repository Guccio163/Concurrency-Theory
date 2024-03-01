var Fork = function () {
  this.state = 0;

  return this;
};

var Conductor = function (maxEating) {
  this.state = maxEating;
  return this;
};

function rightFork(philosopherID) {}

function leftFork() {}

var times = [[], [], [], [], []];
var conductorTimes = [[], [], [], [], []];
var ended = 5;

Fork.prototype.acquire = async function (id, callback, waitingTime = 1) {
  if (this.state == 0) {
    this.state = 1;
    times[id].push(waitingTime);
    callback();
  } else {
    console.log(id, "set timeout for ", waitingTime);
    setTimeout(
      () => this.acquire(id, callback, waitingTime * 2),
      waitingTime * 2
    );
  }
};

Conductor.prototype.call = async function (id, callback, waitingTime = 1) {
  if (this.state > 0) {
    this.state -= 1;
    conductorTimes[id].push(waitingTime);
    callback();
  } else {
    // console.log(philopopherID, "set timeout for ", waitingTime);
    setTimeout(() => this.call(id, callback, waitingTime * 2), waitingTime * 2);
  }
};

Fork.prototype.release = function () {
  this.state = 0;
};

Conductor.prototype.release = function () {
  this.state += 1;
};

var Philosopher = function (id, forks, conductor) {
  this.id = id;
  this.forks = forks;
  this.f1 = id % forks.length;
  this.f2 = (id + 1) % forks.length;
  this.c = conductor;
  return this;
};

async function wypiszDupa(iter, philosopher) {
  for (var i = 0; i < iter; i++) {
    await console.log(philosopher.id, i);
  }
}

async function startNaive(philosopher, count) {
  var forks = philosopher.forks,
    f1 = philosopher.f1,
    f2 = philosopher.f2,
    id = philosopher.id;

  if (count > 0) {
    forks[f1].acquire(id, () => {
      console.log(id, "picked up", f1, " for ", 10 - count, ", his right");
      forks[f2].acquire(id, () => {
        console.log(id, "picked up", f2, ", his left");
        setTimeout(() => {
          console.log(id, "finished eating");
          forks[f1].release();
          forks[f2].release();
          setTimeout(() => {
            startNaive(philosopher, count - 1);
          }, 0);
        }, 0);
      });
    });
  } else {
    ended -= 1;
  }
}

async function startAsym(philosopher, count) {
  var forks = philosopher.forks,
    id = philosopher.id;

  if (id % 2 == 0) {
    var f1 = philosopher.f1,
      f2 = philosopher.f2;
  } else {
    var f1 = philosopher.f2,
      f2 = philosopher.f1;
  }

  if (count > 0) {
    forks[f1].acquire(id, () => {
      console.log(id, "picked up", f1, " for ", 10 - count, ", his right");
      forks[f2].acquire(id, () => {
        console.log(id, "picked up", f2, ", his left");
        setTimeout(() => {
          console.log(id, "finished eating");
          forks[f1].release();
          forks[f2].release();
          setTimeout(() => {
            startAsym(philosopher, count - 1);
          }, 1000 + Math.random() * 1000);
        }, 0);
      });
    });
  } else {
    ended -= 1;
  }
}

async function startConductor(philosopher, count) {
  var forks = philosopher.forks,
    f1 = philosopher.f1,
    f2 = philosopher.f2,
    id = philosopher.id,
    conductor = philosopher.c;

  if (count > 0) {
    conductor.call(id, () => {
      forks[f1].acquire(id, () => {
        console.log(id, "picked up", f1, " for ", 10 - count, ", his right");
        forks[f2].acquire(id, () => {
          console.log(id, "picked up", f2, ", his left");
          setTimeout(() => {
            console.log(id, "finished eating");
            forks[f1].release();
            forks[f2].release();
            conductor.release();
            setTimeout(() => {
              startConductor(philosopher, count - 1);
            }, 1000 + Math.random() * 1000);
          }, 0);
        });
      });
    });
  } else {
    ended -= 1;
  }
}

function printTimes() {
  if (ended == 0) {
    sumTimes = [];
    for (let i = 0; i < times.length; i++) {
      let arr = times[i];
      let sum = arr.reduce(
        (accumulator, currentValue) => accumulator + currentValue,
        0
      );
      sumTimes.push(sum);
    }
    console.log(sumTimes);
    conductorSum = [];
    for (let i = 0; i < conductorTimes.length; i++) {
      let arr = conductorTimes[i];
      let sum = arr.reduce(
        (accumulator, currentValue) => accumulator + currentValue,
        0
      );
      conductorSum.push(sum);
    }
    console.log(conductorSum);
  } else {
    setTimeout(printTimes, 2000);
  }
}

var N = 5;
var forks = [];
var philosophers = [];
var conductor = new Conductor(4);

for (var i = 0; i < N; i++) {
  forks.push(new Fork());
}

for (var i = 0; i < N; i++) {
  philosophers.push(new Philosopher(i, forks, conductor));
  console.log(i, " siadł");
}

for (var i = 0; i < N; i++) {
  startNaive(philosophers[i], 10);
  // startAsym(philosophers[i], 10);
  // startConductor(philosophers[i], 10);
}
printTimes();

// waiting times to acquire fork: (philosophers waiting random time before eating again)
// naive:                 [ 20, 22, 20, 22, 21 ], [ 20, 21, 20, 22, 21 ], [ 20, 21, 20, 22, 21 ]
// asymetric:             [ 20, 21, 23, 20, 21 ], [ 20, 21, 23, 20, 21 ], [ 20, 21, 23, 20, 21 ]
// conductor:             [ 20, 21, 20, 21, 21 ], [ 20, 21, 20, 22, 21 ], [ 20, 21, 20, 21, 21 ]
// waiting for conductor: [ 10, 10, 10, 10, 11 ], [ 10, 10, 10, 10, 11 ], [ 10, 10, 10, 10, 11 ]

// waiting times to acquire fork: (philosophers waiting 0 before eating again)
// naive:                 [ 37, 35, 29, 34, 27 ], [ 33, 25, 39, 42, 26 ], [ 20, 66, 45, 42, 46 ] 1st, 3rd, 4th, 5th 7h and 8th time was a deadlock
// asymetric:             [ 20, 21, 23, 20, 21 ], [ 20, 21, 23, 20, 21 ], [ 20, 21, 23, 20, 21 ]
// conductor:             [ 20, 21, 20, 21, 21 ], [ 20, 21, 20, 21, 21 ], [ 20, 21, 20, 21, 21 ]
// waiting for conductor: [ 10, 10, 10, 10, 11 ], [ 10, 10, 10, 10, 11 ], [ 10, 10, 10, 10, 11 ]
