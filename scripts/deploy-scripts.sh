java ScriptSigner sign key.priv to-autoline.js
java ScriptSigner sign key.priv place-if-on-right-side.js
java ScriptSigner sign key.priv place-if-on-left-side.js
java ScriptSigner sign key.priv pivot-to-switch-and-place.js

scp *.js *.sig admin@10.68.44.2:/U/autos