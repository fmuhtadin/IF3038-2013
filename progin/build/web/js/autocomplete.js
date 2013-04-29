var assignee = new Array("Dekha Anggareska","David Eko Wibowo","Tino Eka Krisna Sambora");
assignee.sort();

function autoassignee(objek, event) {
	if ((!objek) || (!event) || (assignee.length == 0)) {
		return;
	}

	if (objek.value.length == 0) {
		return;
	}

	var elm = (objek.setSelectionRange) ? event.which : event.keyCode;

	if ((elm < 32) || (elm >= 33 && elm <= 46) || (elm >= 112 && elm <= 123)) {
		return;
	}

	var txt = objek.value.replace(/;/gi, ",");
	elm = txt.split(",");
	txt = elm.pop();
	txt = txt.replace(/^\s*/, "");

	if (txt.length == 0) {
		return;
	}

	if (objek.createTextRange) {
		var rng = document.selection.createRange();
		if (rng.parentElement() == objek) {
			elm = rng.text;
			var ini = objek.value.lastIndexOf(elm);
		}
	} else if (objek.setSelectionRange) {
		var ini = objek.selectionStart;
	}

	for (var i = 0; i < assignee.length; i++) {
		elm = assignee[i].toString();

		if (elm.toLowerCase().indexOf(txt.toLowerCase()) == 0) {
			objek.value += elm.substring(txt.length, elm.length);
			break;
		}
	}

	if (objek.createTextRange) {
		rng = objek.createTextRange();
		rng.moveStart("character", ini);
		rng.moveEnd("character", objek.value.length);
		rng.select();
	} else if (objek.setSelectionRange) {
		objek.setSelectionRange(ini, objek.value.length);
	}
}

