'use strict';

var util = require('util');
var path = require('path');
var fs = require('fs');
var request = require('xhr-request');
var Bot = require('slackbots');

var MastermindBot = function Constructor(settings) {
    this.settings = settings;
    this.settings.name = settings.name;
    this.user = null;
    this.game_api_uri = settings.game_api_uri;
}

util.inherits(MastermindBot, Bot);

module.exports = MastermindBot

MastermindBot.prototype.run = function () {
        MastermindBot.super_.call(this, this.settings);
        this.on('start', this._onStart);
        this.on('message', this._onMessage);
};

MastermindBot.prototype._onStart = function () {
        console.log('calling init functions')
        this._loadBotUser();
        this._firstRunCheck();
};

MastermindBot.prototype._loadBotUser = function () {
        console.log('loading bot user')
        var self = this;
        this.user = this.users.filter(function (user) {
            return user.name === self.name
        })[0];
};

MastermindBot.prototype._firstRunCheck = function () {
    this.postMessageToChannel(this.channels[0].name, 'Hi guys, would you like to play? type help for information', {as_user: true});
};

MastermindBot.prototype._onMessage = function (message) {
    var self = this

    if (this._isChatMessage(message) 
        && this._isChannelConversation(message)
        && !this._isFromMastermindBot(message)) {
       
        this._callAPI(message);
    };

};

MastermindBot.prototype._isChatMessage = function(message) {
    return message.type === 'message' && Boolean(message.text);
};

MastermindBot.prototype._isChannelConversation = function(message) {
    return typeof message.channel === 'string' && message.channel[0] === 'C' 
};

MastermindBot.prototype._isFromMastermindBot = function (message) {
    return message.user === this.user.id;
};

MastermindBot.prototype._getChannelById = function (channelId) {
    return this.channels.filter(function (item) {
        return item.id === channelId;
    })[0];
};

MastermindBot.prototype._callAPI = function (message) {
    var channel = this._getChannelById(message.channel);
    var self = this;
    request(self.game_api_uri, {
        method: 'POST',
        json: true,
        responseType: 'json',
        body: message
    }, function (err, response) {
        self.postMessageToChannel(channel.name, response.text, {as_user: true});
    })
}

