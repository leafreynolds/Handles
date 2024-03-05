## Events:
### `onTardisEntered`

Triggered when the tardis has been entered by a player. Also gives the name of the player.

#### Example

```lua
local event, player = os.pullEvent() 
if event == 'onTardisEntered' then
  --do stuff
end
```

---

### `onDoorClosed`

Triggered when the main tardis door has been closed

#### Example

```lua
if os.pullEvent() == 'onDoorClosed' then
  --do stuff
end
```

---

### `onDoorOpened`

Triggered when the main tardis door has been opened

#### Example

```lua
if os.pullEvent() == 'onDoorOpened' then
  --do stuff
end
```

---

### `onShellChanged`

Triggered when the shell has been changed. Gives you the name of the shell that's been changed to.

#### Example

```lua
local event, shellTheme = os.pullEvent() 
if event == 'onShellChanged' then 
  print(shellTheme) 
end
```

---

### `onTakeOff`

Triggered when the tardis is taking off

#### Example

```lua
if os.pullEvent() == 'onTakeOff' then
  --do stuff
end
```

---

### `onCrashed`

Triggered when the tardis crashes. Returns the x y z co-ords, as well as the facing direction and dimension crashed in.

#### Example

```lua
local event = {os.pullEvent()} 
if event[1] == 'onCrashed' then 
  print(event[2], event[3], event[4], event[5], event[6]) 
end
```

---

