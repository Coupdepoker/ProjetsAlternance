import { Character } from './hyrule_castle';

export function escape(user : Character, enemy : Character) {
  enemy.health = 0;
  user.strength -= 1;
  console.log('Escape: You lose one point of Strength.');
}

export function protect(user : Character, enemy : Character) {
  user.health += enemy.strength / 2;
  console.log('Protection Activated.');
}
