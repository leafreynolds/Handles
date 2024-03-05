## Functions:
### `getDimensions`

Gets the whitelisted list of dimensions that a tardis can travel to.

#### Returns

An iterable list of dimension strings, in the format of 'mod:dimension_name'
#### Example

```lua
local dimensionList = tardis.getDimensions()
```

---

### `canBeginFlight`

Determines if the Tardis can begin flight.

#### Returns

A boolean value - true if can begin flight, else false.
#### Example

```lua
local canBeginFlight = tardis.canBeginFlight()
```

---

### `canEndFlight`

Determines if the TARDIS can end flight.

#### Returns

A boolean value indicating if the TARDIS can end flight.
#### Example

```lua
local canEndFlight = tardis.canEndFlight()
```

---

### `setAutoLand`

Sets whether this flight should be stabilized. Is only useful if set mid-flight.

#### Arguments

* `autoLand` - `boolean`

#### Example

```lua
tardis.setAutoLand(false)
```

---

### `setTargetPosition`

Sets the target position coordinate. Takes in an x,y,z coordinate as ints.

#### Arguments

* `x` - `int`
* `y` - `int`
* `z` - `int`

#### Example

```lua
tardis.setTargetPosition(122,90,-12)
```

---

### `beginFlight`

Starts the TARDIS flight.

#### Arguments

* `stabilizedFlight` - `boolean`

#### Example

```lua
tardis.beginFlight(false) -- for activating non stabilized flight
```

---

### `getTargetLocation`

Gets the target location, multiple values that determine where the Tardis will try go once flight has begun.w

#### Returns

Returns x, y, z, facingDirection, dimensionID.
#### Example

```lua
local x, y, z, facing, dimensionID = tardis.getTargetLocation()
```

---

### `endFlight`

Stops the TARDIS flight, but only if the return value of canEndFlight is true

#### Example

```lua
tardis.endFlight()
```

---

### `getFlightPercent`

Obtains the flight percentage of the TARDIS.

#### Returns

A percentage float value between 0 - 1.
#### Example

```lua
local flightPercent = tardis.getFlightPercent()
```

---

### `getIsLanding`

Determines if the TARDIS has begun the landing sequence.

#### Returns

A boolean value indicating if the TARDIS is landing.
#### Example

```lua
local getIsLanding = tardis.getIsLanding()
```

---

### `setTargetDirection`

Sets the target facing direction. Takes in a string, representing north, east, south or west.

#### Arguments

* `dir` - `String`

#### Returns

direction if able to set, else throws error
#### Example

```lua
tardis.setTargetDirection('west')
```

---

### `getDoorLocked`

Gets the current door locked status

#### Returns

Two values - internal door locked, external door locked
#### Example

```lua
local locked = tardis.getDoorLocked()
```

---

### `setTargetDimension`

Sets the target dimension, takes in a string - in the format of 'mod:dimension_id'

#### Arguments

* `dimensionName` - `String`

#### Example

```lua
tardis.setTargetDimension('overworld')
```

---

### `getTargetDirection`

Gets the target facing direction

#### Returns

Direction, in lower case, ie 'south'
#### Example

```lua
tardis.getTargetDirection()
```

---

### `getTargetDimension`

Gets the target dimension

#### Returns

a string - in the format of 'mod:dimension_id'
#### Example

```lua
local targetDim = tardis.getTargetDimension()
```

---

### `getTargetPosition`

Gets the target position coordinate.

#### Returns

X,Y,Z as ints
#### Example

```lua
local x, y, z = tardis.getTargetPosition()
```

---

### `getIsOnCooldown`

Whether your tardis is currently in cooldown mode. Happens after crashing your tardis.

#### Returns

true if on cooldown, else false
#### Example

```lua
local onCooldown = tardis.getIsOnCooldown()
```

---

### `getCanUseControls`

Gets whether the controls can be used. Typically controls will always be available, unless you have crashed your tardis.

#### Returns

true if controls can be interacted with, otherwise false.
#### Example

```lua
local isCrashing = tardis.getIsCrashing()
```

---

### `getIsAutoLandSet`

Gets whether flight should be stabilized. Is only useful mid-flight.

#### Returns

whether auto land is set
#### Example

```lua
local stabilized = tardis.getIsAutoLandSet()
```

---

### `getExteriorTheme`

gets the current exterior shell theme

#### Returns

the name of the current shell theme
#### Example

```lua
local exteriorTheme = tardis.getExteriorTheme()
```

---

### `getIsCrashing`

Gets whether the tardis is currently in the process of crashing

#### Returns

true or false depending on crash state
#### Example

```lua
local isCrashing = tardis.getIsCrashing()
```

---

### `setDoorLocked`

Set the lock state of your tardis doors. Always closes your doors.

#### Arguments

* `locked` - `boolean`

#### Example

```lua
tardis.setDoorLocked(false)
```

---

### `getShellThemes`

Gets all the shell themes that are available

#### Returns

an iterable list of shell themes
#### Example

```lua
local shellThemesList = tardis.getShellThemes()
```

---

### `getRequiredFlightEvents`

The total number of flight events you will need to complete in order to make it safely to your destination.

#### Returns

An int value - required number of control requests
#### Example

```lua
local requiredFlightEvents = tardis.getRequiredFlightEvents()
```

---

### `getShellThemePatterns`

Gets all the shell pattern ids for the given theme name

#### Arguments

* `arg0` - `String`

#### Returns

an iterable list of shell pattern ids
#### Example

```lua
local shellPatternsList = tardis.getShellThemePatterns('shellThemeName')
```

---

### `getFlightEventActive`

During active flight, will tell you whether your tardis is waiting for you to interact with a control.

#### Returns

A boolean value indicating if the TARDIS has an active flight event.
#### Example

```lua
local flightEventActive = tardis.getFlightEventActive()
```

---

### `getInternalDoorOpen`

Gets whether your primary internal door is open or not.

#### Returns

true if open, false if not.
#### Example

```lua
local doorOpen = tardis.getInternalDoorOpen()
```

---

### `areDangerZoneEventsComplete`

Checks whether all the Danger Zone events are complete

#### Returns

A boolean value - true if events are complete, false if not
#### Example

```lua
local areDangerZoneEventsComplete = tardis.areDangerZoneEventsComplete()
```

---

### `getLastKnownDimension`

Gets the last known exterior dimension

#### Returns

the dimension id, in the format of 'mod:dimension_name'
#### Example

```lua
local dimensionID = tardis.getLastKnownDimension()
```

---

### `getLastKnownDirection`

Gets the last known exterior facing direction

#### Returns

the direction facing string id
#### Example

```lua
local facing = tardis.getLastKnownDirection()
```

---

### `getRespondedFlightEvents`

Gets the total number of flight events you have already responded to

#### Returns

An int value - total control requests already responded to
#### Example

```lua
local respondedFlightEvents = tardis.getRespondedFlightEvents()
```

---

### `getFlightEventControl`

During active flight, if there is a flight event, tells you which control it's waiting for you to interact with.

#### Returns

A string value, the name id of the control that is waiting for a response.
#### Example

```lua
local currentFlightEventControl = tardis.getFlightEventControl()
```

---

### `areControlEventsComplete`

Checks whether all the flight events are complete

#### Returns

A boolean value - true if events are complete, false if not
#### Example

```lua
local areControlEventsComplete = tardis.areControlEventsComplete()
```

---

### `setDoorClosed`

Sets your doors open/closed state

#### Arguments

* `closed` - `boolean`

#### Example

```lua
tardis.setDoorClosed(true)
```

---

### `setShellTheme`

Sets the current shell theme to the given id

#### Arguments

* `shellTheme` - `String`

#### Example

```lua
tardis.setShellTheme('shellThemeName')
```

---

### `isInDangerZone`

If you have missed too many flight events, you will be in the danger zone. This requires you to complete a series of danger zone requests.

#### Returns

A boolean value - indicates if the TARDIS is in the danger zone.
#### Example

```lua
local isInDangerZone = tardis.isInDangerZone()
```

---

### `setShellPattern`

Allows you to set a shell pattern, based on a pattern theme

#### Arguments

* `shellTheme` - `String`
* `shellPattern` - `String`

#### Example

```lua
tardis.setShellPattern('shellTheme', 'shellPattern')
```

---

### `isInFlight`

Obtains the flight status of the TARDIS.

#### Returns

A boolean value indicating if the TARDIS is in flight.
#### Example

```lua
local isInFlight = tardis.isInFlight()
```

---

### `setTargetLocation`

Sets the target location information. Takes in an x,y,z coordinate, a string of the facing direction, a string of the dimension ID

#### Arguments

* `x` - `int`
* `y` - `int`
* `z` - `int`
* `directionName` - `String`
* `dimensionID` - `String`

#### Example

```lua
tardis.setTargetLocation(122,90,-12,'south','overworld' )
```

---

### `getLastKnownLocation`

Gets the last known exterior shell location

#### Returns

x,y,z,direction,dimension
#### Example

```lua
local x, y, z, facing, dimensionID = tardis.getLastKnownLocation()
```

---

