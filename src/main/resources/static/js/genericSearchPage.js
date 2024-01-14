$('#addAttribute').click(function() {

    var selectedAttribute = $('select[class="form-control js-selectedNewAttribute"]').val();

    if (selectedAttribute !== '') {
        var attributeRow = $('<div>').addClass('attribute-row form-row');
        attributeRow.append('<div class="col"> <label  class="col-sm-2 col-form-label">Kolon</label><select class="form-control" name="selectedAttribute"><option value="' + selectedAttribute + '">' + selectedAttribute + '</option></select></div>');
        attributeRow.append('<div class="col-5"> <label  class="col-sm-2 col-form-label">Deger</label><input class="form-control" type="text" name="attributeValues" placeholder="Attribute Value" /></div>');
        attributeRow.append('<div class="col"><label  class="col-sm-2 col-form-label">Filtre</label> <select class="form-control" name="filterTypes"><option value="equals">Esittir</option><option value="contains">İçerir</option></option><option value="notContains">İçermeyen</option><option value="startsWith">Ile Başlayanlar</option><option value="endsWith">Ile Bitenler</option><option value="isEmpty">Datasi Bos</option><option value="isNotEmpty">Datasi Dolu</option></select></div>');
        attributeRow.append('<div class="col"><label  class="col-sm-2 col-form-label">İşlemler</label><button type="button" class="btn btn-danger remove-attribute form-control" >Sil</button></div>');
        attributeRow.insertAfter('#logicalOperatorFormGroup');
    }
});

// Remove Attribute button click event
$(document).on('click', '.remove-attribute', function() {
    $(this).closest('.attribute-row').remove();
});