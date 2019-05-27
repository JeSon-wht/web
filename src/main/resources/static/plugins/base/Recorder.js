define(['Base', pluginJS('record/recorder')], function (Base, Recorder) {
    return {
        create: function (options) {
            var opts = {
                sampleRate: 44100, //采样频率，默认为44100Hz(标准MP3采样率)
                bitRate: 128, //比特率，默认为128kbps(标准MP3质量)
                success: function () {
                },
                error: function (msg) { //失败回调函数
                    Base.error(msg);
                },
                fix: function (msg) { //不支持H5录音回调函数
                    Base.error(msg);
                }
            };
            this.recorder = new Recorder($.extend(opts, options));
            return this;
        },
        start: function () {
            this.recorder.start();
        },
        stop: function (callback) {
            this.recorder.stop();
            this.recorder.getBlob(callback);
        }
    };
});