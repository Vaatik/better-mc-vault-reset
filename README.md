# BetterMCVaultReset

A Paper plugin that allows players to re-loot Trial Chamber vaults after a configurable cooldown.

## Features

- Reset vaults (normal and ominous) after cooldown
- Configurable cooldown duration
- Per-player, per-vault cooldown tracking
- Discreet action bar messages
- Admin command `/vaultreset reload`

## Requirements

- Paper 1.21.4+
- Java 21

## Installation

1. Download `BetterMCVaultReset-x.x.x.jar` from [Releases](https://github.com/Vaatik/better-mc-vault-reset/releases)
2. Place in your server's `plugins/` folder
3. Restart the server
4. Edit `plugins/BetterMCVaultReset/config.yml` to customize

## Configuration

```yaml
cooldown: "4h"  # Supports: 30s, 5m, 2h, 1d
```

## Commands

| Command | Description | Permission |
|---------|-------------|------------|
| `/vaultreset reload` | Reload the configuration | `vaultreset.admin` |

## How It Works

1. Player loots a vault normally
2. Plugin starts tracking cooldown for that player/vault
3. After cooldown expires, player can right-click the vault to reset it
4. Player can loot the vault again

## License

[CC BY-NC 4.0](LICENSE) - Free to use and modify, but not for commercial purposes.