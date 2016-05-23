
'use strict';

var MastermindBot = require('./mastermind.js');

var token = process.env.BOT_API_KEY;
var name  = process.env.BOT_NAME;
var game_api_uri = process.env.GAME_API_URI;

var mastermindbot = new MastermindBot({
    token: token, 
    name: name,
    game_api_uri: game_api_uri
});

console.log('starting mastermind-bot')
mastermindbot.run();
