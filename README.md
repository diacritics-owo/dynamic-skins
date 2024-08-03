# Dynamic Skins

Set your skin dynamically based on conditions (client-only for now)

## Getting Started

After installing the mod and launching the game once, a file called `dynamic-skins.js` will be written to the configuration folder (if you don't know where that is, Dynamic Skins will helpfully tell you the file path in the logs on the first launch).

Dynamic Skins exposes a very simple JavaScript API that allows you to replace player skins based on conditions that you define. **Note that the skin replacement only takes place on _your_ client. Only you will see the modified skins.** Also note that this only changes skins in-game, so skin selector mods or the tab list/social interactions screen will not be affected.

Example configuration:

```javascript
if (
  data.target.player.profile.username === data.client.player.profile.username && // Only replace your own skin - without this, you'll see that everyone's skin changes when you swim
  data.client.player.isTouchingWater &&
  data.client.player.isSwimming
) {
  skin.set("minecraft:textures/entity/zombie/drowned_outer_layer.png");
}
```

Another:

```javascript
// When anyone holds a trident, their skin will be changed
if (data.target.player.equipment.mainhand === "minecraft:trident") {
  skin.set("minecraft:textures/entity/zombie/drowned_outer_layer.png");
}
```

This configuration will be run once per player rendered per frame.

## Configuration Cache

The configuration is read once on mod initialisation and cached in memory, and updates to it will not be read unless you run `/dynamic-skins cache reload`. Alternatively, you can open the Mod Menu configuration page for Dynamic Skins and click "Reload configuration". Every time you update the configuration, you must reload it for it to apply.

## Errors

If an error is encountered when Dynamic Skins executes the configuration, the error and a message stating that Dynamic Skins has stopped will be logged. You can run the command `/dynamic-skins error` to display the error. To start it again, you must either run `/dynamic-skins error reset` or open the Mod Menu page (reopen if it was already open) and click the "Restart" button that should now be visible. Note that restarting will reload the configuration.

## Custom Skins

For now, Dynamic Skins only allows you to set a skin via a resource location (of the form `namespace:path/to/image.png`). To use custom skins, you can create a [resource pack](https://minecraft.wiki/w/Resource_pack#Java_Edition).

Example resource pack structure:

```
|- pack.mcmeta
|- pack.png
|- assets
   |- skins
      |- my_skin.png
```

You could then use `skin.set('skins:my_skin.png')`.

## API

### `Skin`

#### `value(): string`

#### `set(value: string)`

---

### `Data`

#### `client: ClientData`

#### `target: TargetData`

---

### `ClientData`

#### `player: PlayerData`

---

### `TargetData`

#### `player: PlayerData`

---

### `PlayerData`

#### `profile: ProfileData`

#### `equipment: EquipmentData`

#### `isTouchingWater: boolean`

#### `isClimbing: boolean`

#### `isSwimming: boolean`

#### `isSneaking: boolean`

#### `isOnFire: boolean`

#### `isInLava: boolean`

#### `isSprinting: boolean`

---

### `ProfileData`

#### `username: string`

---

### `EquipmentData`

#### `head: string`

#### `chest: string`

#### `legs: string`

#### `feet: string`

#### `mainhand: string`

#### `offhand: string`
