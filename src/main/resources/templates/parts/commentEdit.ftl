<form method="post">
	<div class="form-group mb-3">
		<div class="form-group">
			<input class="form-control ${(textError??)?string('is-invalid', '')}" type="text" name="text" placeholder="Enter your comment" value="${(comment.text)!''}"/>
			<#if textError??>
				<div class="invalid-feedback">${textError}</div>
			</#if>
		</div>
	</div>
	<input type="hidden" name="_csrf" value="${_csrf.token}" />
	<button class="btn btn-primary" type="submit" value="edit" name="button">
		<#if isCommentEdit>
			edit
		<#else>
			add
		</#if>
	<#if isCommentEdit>
		<button class="btn btn-primary ml-2" type="submit" value="delete" name="button">delete</button>
	</#if>
</form>