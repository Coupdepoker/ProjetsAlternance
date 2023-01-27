import { escape, protect } from './better_combat_options';

const fs = require('fs');

export interface Character {
  health : number;
  strength : number;
  name : string;
  maxHP : number;
}

const rls = require('readline-sync');

function displayInfosFighters(a : Character, b :Character) {
  console.log(`Enemy: ${b.name} Health:${b.health} Strength:${b.strength}`);
  console.log(`YourCharacter: ${a.name} Health:${a.health} Strength:${a.strength}`);
}

function displayOptions() {
  console.log('--Options--');
  console.log('1. Attack 2. Heal 3.Escape 4.Protect');
}

function enemyAttack(a :Character, b :Character) {
  console.log('Enemy attacks!');
  a.health -= b.strength;
  displayInfosFighters(a, b);
}

function ask(a : Character, b :Character) {
  let response = '';
  displayOptions();
  response = rls.question('What do you want to do?');
  if (response === 'Attack' || response === '1') {
    console.log('You attack!');
    b.health -= a.strength;
    enemyAttack(a, b);
  } else if (response === 'Heal' || response === '2') {
    console.log('You Heal.');
    const tmp = a.health + a.maxHP / 2;
    if (tmp >= a.maxHP) {
      a.health = a.maxHP;
    } else {
      a.health = tmp;
    }
    enemyAttack(a, b);
  } else if (response === 'Escape' || response === '3') {
    escape(a, b);
  } else if (response === 'Protect' || response === '4') {
    protect(a, b);
    enemyAttack(a, b);
  } else {
    console.log('Wrong Input');
    ask(a, b);
  }
}

function chooseCharacters(jsonFile : string) {
  let content;
  try {
    const file = fs.readFileSync(jsonFile, 'utf-8');
    content = JSON.parse(file);
  } catch (error) {
    content = null;
  }
  const tab = [];
  if (content === null) {
    process.stderr.write('Wrong use of the program.\n');
  } else {
    const randNum = Math.random();
    for (const js of content) {
      if (randNum >= 0 && randNum < 0.5 && js.rarity === 1) {
        tab.push(js);
      } else if (randNum >= 0.5 && randNum < 0.8 && js.rarity === 2) {
        tab.push(js);
      } else if (randNum >= 0.8 && randNum < 0.95 && js.rarity === 3) {
        tab.push(js);
      } else if (randNum >= 0.95 && randNum < 0.99 && js.rarity === 4) {
        tab.push(js);
      } else if (randNum >= 0.99 && randNum < 1 && js.rarity === 5) {
        tab.push(js);
      }
    }
  }
  return tab;
}

function getRandomInt(max : number) {
  return Math.floor(Math.random() * max);
}

function chooseMainChar(x : Character, fileName :string) {
  const tab = chooseCharacters(fileName);
  const num : number = getRandomInt(tab.length);
  x.health = tab[num].hp;
  x.maxHP = tab[num].hp;
  x.strength = tab[num].str;
  x.name = tab[num].name;
}

function enemies() {
  const tab = [];
  for (let i = 0; i < 10; i += 1) {
    if (i < 9) {
      const enemy : Character = {
        health: 0,
        strength: 0,
        name: '',
        maxHP: 0,
      };
      chooseMainChar(enemy, 'enemies.json');
      tab.push(enemy);
    } else {
      const boss : Character = {
        health: 0,
        strength: 0,
        name: '',
        maxHP: 0,
      };
      chooseMainChar(boss, 'bosses.json');
      tab.push(boss);
    }
  }
  return tab;
}

function main() {
  const yourChar : Character = {
    health: 0,
    strength: 0,
    name: '',
    maxHP: 0,
  };
  chooseMainChar(yourChar, 'players.json');
  const enemiesT = enemies();

  for (let i = 0; i < enemiesT.length; i += 1) {
    console.clear();
    console.log(`== FIGHT ${i + 1} ==`);
    displayInfosFighters(yourChar, enemiesT[i]);
    console.log('');
    while (yourChar.health > 0 && enemiesT[i].health > 0) {
      ask(yourChar, enemiesT[i]);
    }
    if (yourChar.health <= 0) {
      break;
    }
  }
  if (yourChar.health <= 0) {
    console.log('Looser! Ha Ha!');
  } else {
    console.log('Congratulations! You are the best.');
  }
}

main();
