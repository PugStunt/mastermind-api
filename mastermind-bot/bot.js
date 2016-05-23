
'use strict';

var MastermindBot = require('./mastermind.js');

var token = 'xoxb-44847266163-Xtik9aFRCC3hholevKyvZomY'

var mastermindbot = new MastermindBot({
    token: token, name: 'mastermind'
});

console.log('starting mastermind-bot')
mastermindbot.run();
