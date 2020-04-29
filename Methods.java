public String toRoman(int i) {
		String romanNumber = "+X";
		switch (i) {
			case 1: {
				romanNumber = "I";
				break;
			}
			case 2: {
				romanNumber = "II";
				break;
			}
			case 3: {
				romanNumber = "III";
				break;
			}
			case 4: {
				romanNumber = "IV";
				break;
			}
			case 5: {
				romanNumber = "V";
				break;
			}
			case 6: {
				romanNumber = "VI";
				break;
			}
			case 7: {
				romanNumber = "VII";
				break;
			}
			case 8: {
				romanNumber = "VIII";
				break;
			}
			case 9: {
				romanNumber = "IX";
				break;
			}
			case 10: {
				romanNumber = "X";
				break;
			}
			default: {
				romanNumber = "+X";
				break;
			}
		}
		return romanNumber;
	}

	public List<String> getEffects(Player p) {
		List<String> effects = new ArrayList<>();
		if (p.getActivePotionEffects().isEmpty()) {
			effects.add(" &8» &fNone");
			return effects;
		}
		for (PotionEffect effect : p.getActivePotionEffects()) {
			if (effect.getDuration() >= 32760) {
				effects.add(" &8» &a" + convertEffects(effect) + " " + String.valueOf(toRoman(effect.getAmplifier() + 1)) + ":" + " &fInfinite");
			} else {
				effects.add(" &8» &a" + convertEffects(effect) + " " + String.valueOf(toRoman(effect.getAmplifier() + 1)) + ":" + " &f" + convertTime(effect.getDuration()));
			}
		}
		return effects;
	}

	public String convertTime(long ticks) {
		long seconds1 = ticks * 1 / 20;
		long minutes = TimeUnit.SECONDS.toMinutes(seconds1);
		long seconds = seconds1 - TimeUnit.MINUTES.toSeconds(minutes);
		return minutes + ":" + seconds;

	}

	public String convertEffects(PotionEffect effect) {
		String effects = "Unknown";
		if (effect.getType().getName().equalsIgnoreCase("Absorption")) {
			effects = "Absorption";
		} else if (effect.getType().getName().equalsIgnoreCase("Blindness")) {
			effects = "Blindness";
		} else if (effect.getType().getName().equalsIgnoreCase("Confusion")) {
			effects = "Confusion";
		} else if (effect.getType().getName().equalsIgnoreCase("Damage_resistance")) {
			effects = "Resistance";
		} else if (effect.getType().getName().equalsIgnoreCase("Fast_digging")) {
			effects = "Fast Digging";
		} else if (effect.getType().getName().equalsIgnoreCase("Fire_resistance")) {
			effects = "Fire Resistance";
		} else if (effect.getType().getName().equalsIgnoreCase("Harm")) {
			effects = "Instant Damage";
		} else if (effect.getType().getName().equalsIgnoreCase("Heal")) {
			effects = "Heal";
		} else if (effect.getType().getName().equalsIgnoreCase("Health_boost")) {
			effects = "Health Boost";
		} else if (effect.getType().getName().equalsIgnoreCase("Hunger")) {
			effects = "Hunger";
		} else if (effect.getType().getName().equalsIgnoreCase("INCREASE_DAMAGE")) {
			effects = "Strength";
		} else if (effect.getType().getName().equalsIgnoreCase("Invisibility")) {
			effects = "Invisibility";
		} else if (effect.getType().getName().equalsIgnoreCase("Jump")) {
			effects = "Jump Boost";
		} else if (effect.getType().getName().equalsIgnoreCase("Nigth_vision")) {
			effects = "Night Vision";
		} else if (effect.getType().getName().equalsIgnoreCase("Poison")) {
			effects = "Poison";
		} else if (effect.getType().getName().equalsIgnoreCase("Regeneration")) {
			effects = "Regeneration";
		} else if (effect.getType().getName().equalsIgnoreCase("Saturation")) {
			effects = "Saturation";
		} else if (effect.getType().getName().equalsIgnoreCase("Slow")) {
			effects = "Slowness";
		} else if (effect.getType().getName().equalsIgnoreCase("Slow_digging")) {
			effects = "Slow Digging";
		} else if (effect.getType().getName().equalsIgnoreCase("Speed")) {
			effects = "Speed";
		} else if (effect.getType().getName().equalsIgnoreCase("WATER_BREATHING")) {
			effects = "Water Breathing";
		} else if (effect.getType().getName().equalsIgnoreCase("Weakness")) {
			effects = "Weakness";
		} else if (effect.getType().getName().equalsIgnoreCase("WITHER")) {
			effects = "Wither";
		}
		return effects;
	}

	public String getRank(Player p) {
		return plugin.getPermissions().getPrimaryGroup(p);
	}

	public String getCardinalDirection(Player player) {
		double rotation = (player.getLocation().getYaw() - 90.0f) % 360.0f;
		if (rotation < 0.0) {
			rotation += 360.0;
		}
		if (0.0 <= rotation && rotation < 22.5) {
			return "W";
		}
		if (22.5 <= rotation && rotation < 67.5) {
			return "NW";
		}
		if (67.5 <= rotation && rotation < 112.5) {
			return "N";
		}
		if (112.5 <= rotation && rotation < 157.5) {
			return "NE";
		}
		if (157.5 <= rotation && rotation < 202.5) {
			return "E";
		}
		if (202.5 <= rotation && rotation < 247.5) {
			return "SE";
		}
		if (247.5 <= rotation && rotation < 292.5) {
			return "S";
		}
		if (292.5 <= rotation && rotation < 337.5) {
			return "SW";
		}
		if (337.5 <= rotation && rotation < 360.0) {
			return "W";
		}
		return null;
	}

	public boolean isPremium(String username) {
		try {
			URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String line;
			StringBuilder result = new StringBuilder();
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
			return !result.toString().equals("");
		} catch (IOException e) {
		}
		return false;
	}

	public String getIP(Player p) {
		String playerIP = p.getAddress().getAddress().toString().replaceAll("/", "");
		return playerIP;
	}

	public String getLang(Player p) {
		try {
			Object ep = this.getMethod("getHandle", p.getClass()).invoke(p, (Object) null);
			Field f = ep.getClass().getDeclaredField("locale");
			f.setAccessible(true);
			return (String) f.get(ep);
		} catch (Exception e) {
		}
		return null;
	}

	private Method getMethod(String name, Class<?> clazz) {
		Method[] arrayOfMethod;
		int j = (arrayOfMethod = clazz.getDeclaredMethods()).length;
		for (int i = 0; i < j; i++) {
			Method m = arrayOfMethod[i];
			if (m.getName().equals(name)) {
				return m;
			}
		}
		return null;
	}

	public static String getCountry(String ip) {
		try {
			URL url = new URL("http://ip-api.com/json/" + ip);
			BufferedReader stream = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuilder entirePage = new StringBuilder();
			String inputLine;
			while ((inputLine = stream.readLine()) != null) {
				entirePage.append(inputLine);
			}
			stream.close();
			if (!(entirePage.toString().contains("\"country\":\""))) {
				return "Unknown";
			}
			return entirePage.toString().split("\"country\":\"")[1].split("\",")[0];
		} catch (Exception e) {
		}
		return "Unknown";
	}

	public static String getCity(String ip) {
		try {
			URL url = new URL("http://ip-api.com/json/" + ip);
			BufferedReader stream = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuilder entirePage = new StringBuilder();
			String inputLine;
			while ((inputLine = stream.readLine()) != null) {
				entirePage.append(inputLine);
			}
			stream.close();
			if (!(entirePage.toString().contains("\"city\":\""))) {
				return "Unknown";
			}
			return entirePage.toString().split("\"city\":\"")[1].split("\",")[0];
		} catch (Exception e) {
		}
		return "Unknown";
	}

	public int getPing(Player p) {
		try {
			Object entityPlayer = p.getClass().getMethod("getHandle").invoke(p);
			int ping = (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
			return ping;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
		return 0;
	}